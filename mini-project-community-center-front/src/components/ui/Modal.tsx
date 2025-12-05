import { useEffect, useState, useCallback, useMemo } from 'react';
import * as Dialog from '@radix-ui/react-dialog';
import type { ReviewFormData } from '@/types/review/review.type';
import type { ReviewRating } from '@/types/review/review.enum.type';
import { Select, SelectItem } from '@/components/ui/Select';
import '@/styles/common/modal.css';

const MAX_IMAGE_COUNT = 3;

type ModalType = 'info' | 'success' | 'error' | 'warning';

interface ModalAlertOptions {
  title?: string;
  message: string;
  type?: ModalType;
  onClose?: () => void;
  buttonText?: string;
}

interface ModalConfirmOptions {
  title?: string;
  message?: string;
  onConfirm?: () => void;
  onCancel?: () => void;
  confirmText?: string;
  cancelText?: string;
}

interface ModalProps {
  isOpen: boolean;
  onClose: () => void;
  title?: string;
  message?: string;
  type?: ModalType;
  children?: React.ReactNode;
  size?: 'small' | 'normal' | 'large';
}

export const Modal: React.FC<ModalProps> = ({
  isOpen,
  onClose,
  title,
  message,
  type = 'info',
  children,
  size = 'normal'
}) => {
  const sizeClass = useMemo(() => {
    if (size === 'small') return 'modal-small';
    if (size === 'large') return 'modal-large';
    return '';
  }, [size]);

  const modalClassName = useMemo(
    () => `modal modal-${type} ${sizeClass}`.trim(),
    [type, sizeClass]
  );

  return (
    <Dialog.Root open={isOpen} onOpenChange={(open) => !open && onClose()}>
      <Dialog.Portal>
        <Dialog.Overlay className="modal-overlay" />
        <Dialog.Content className={modalClassName}>
          {(title || message) && (
            <div className="modal-header">
              {title && <Dialog.Title className="modal-title">{title}</Dialog.Title>}
              <Dialog.Close asChild>
                <button className="modal-close" aria-label="닫기">
                  &times;
                </button>
              </Dialog.Close>
            </div>
          )}
          <div className="modal-body">
            {message ? (
              <Dialog.Description className="modal-message">{message}</Dialog.Description>
            ) : (
              <Dialog.Description className="sr-only">{title || '모달'}</Dialog.Description>
            )}
            {children}
          </div>
        </Dialog.Content>
      </Dialog.Portal>
    </Dialog.Root>
  );
};

export const useModalAlert = () => {
  const [alertState, setAlertState] = useState<ModalAlertOptions | null>(null);

  const alert = useCallback((options: ModalAlertOptions) => {
    setAlertState(options);
  }, []);

  const handleClose = useCallback(() => {
    alertState?.onClose?.();
    setAlertState(null);
  }, [alertState]);

  const AlertModal = useCallback(() => {
    if (!alertState) return null;

    return (
      <Modal
        isOpen={true}
        onClose={handleClose}
        title={alertState.title || '알림'}
        message={alertState.message}
        type={alertState.type || 'info'}
      >
        <div className="modal-footer">
          <button className="btn btn-primary" onClick={handleClose}>
            {alertState.buttonText || '확인'}
          </button>
        </div>
      </Modal>
    );
  }, [alertState, handleClose]);

  return { alert, AlertModal };
};

export const useModalConfirm = () => {
  const [confirmState, setConfirmState] = useState<(ModalConfirmOptions & { isOpen: boolean }) | null>(null);

  const confirm = useCallback((options: ModalConfirmOptions) => {
    setConfirmState({ ...options, isOpen: true });
  }, []);

  const handleConfirm = useCallback(() => {
    confirmState?.onConfirm?.();
    setConfirmState(null);
  }, [confirmState]);

  const handleCancel = useCallback(() => {
    confirmState?.onCancel?.();
    setConfirmState(null);
  }, [confirmState]);

  const ConfirmModal = useCallback(() => {
    if (!confirmState) return null;

    return (
      <Modal
        isOpen={confirmState.isOpen}
        onClose={handleCancel}
        title={confirmState.title || '확인'}
        message={confirmState.message || '이 작업을 진행하시겠습니까?'}
      >
        <div className="modal-footer">
          <button className="btn btn-secondary" onClick={handleCancel}>
            {confirmState.cancelText || '취소'}
          </button>
          <button className="btn btn-primary" onClick={handleConfirm}>
            {confirmState.confirmText || '확인'}
          </button>
        </div>
      </Modal>
    );
  }, [confirmState, handleConfirm, handleCancel]);

  return { confirm, ConfirmModal };
};

interface ReviewModalProps {
  isOpen: boolean;
  onClose: () => void;
  onSubmit: (data: ReviewFormData) => void;
  courseTitle?: string;
  courseId?: number;
  availableCourses?: Array<{ id: number; title: string; center: { name: string } }>;
  initialRating?: ReviewRating;
  initialContent?: string;
  initialImages?: string[];
}

const toReviewRating = (value: number | undefined): ReviewRating => {
  if (value !== undefined && value >= 1 && value <= 5) {
    return value as ReviewRating;
  }
  return 1;
};

export const ReviewModal: React.FC<ReviewModalProps> = ({
  isOpen,
  onClose,
  onSubmit,
  courseTitle,
  courseId,
  availableCourses = [],
  initialRating,
  initialContent = '',
  initialImages = []
}) => {
  const [selectedCourseId, setSelectedCourseId] = useState<number | undefined>(courseId);
  const [rating, setRating] = useState<ReviewRating>(toReviewRating(initialRating));
  const [content, setContent] = useState(initialContent);
  const [uploadedFiles, setUploadedFiles] = useState<File[]>([]);
  const [imagePreviews, setImagePreviews] = useState<string[]>([]);
  const [savedImages, setSavedImages] = useState<string[]>(initialImages || []);

  useEffect(() => {
    if (!isOpen) return;

    setSelectedCourseId(courseId);
    setRating(toReviewRating(initialRating));
    setContent(initialContent || '');
    setSavedImages(initialImages || []);
    setUploadedFiles([]);
    setImagePreviews([]);
  }, [isOpen, courseId, initialRating, initialContent, initialImages]);

  useEffect(() => {
    if (!isOpen) {
      return;
    }
    
    return () => {
      setImagePreviews(prev => {
        prev.forEach(preview => URL.revokeObjectURL(preview));
        return [];
      });
    };
  }, [isOpen]);

  const totalImageCount = useMemo(
    () => uploadedFiles.length + savedImages.length,
    [uploadedFiles.length, savedImages.length]
  );

  const handleFileChange = useCallback((e: React.ChangeEvent<HTMLInputElement>) => {
    const newFiles = Array.from(e.target.files || []);
    if (!newFiles.length) return;

    const availableSlots = MAX_IMAGE_COUNT - totalImageCount;

    if (availableSlots <= 0) {
      alert('최대 3개까지 파일을 업로드할 수 있습니다.');
      return;
    }

    const filesToAdd = newFiles.slice(0, availableSlots);
    
    setUploadedFiles(prev => [...prev, ...filesToAdd]);
    setImagePreviews(prev => [...prev, ...filesToAdd.map(file => URL.createObjectURL(file))]);

    if (newFiles.length > availableSlots) {
      alert(`최대 3개까지 업로드 가능합니다. ${availableSlots}개만 추가되었습니다.`);
    }
  }, [totalImageCount]);

  const handleRemoveFile = useCallback((index: number) => {
    setImagePreviews(prev => {
      URL.revokeObjectURL(prev[index]);
      return prev.filter((_, i) => i !== index);
    });
    setUploadedFiles(prev => prev.filter((_, i) => i !== index));
  }, []);

  const handleRemoveSavedImage = useCallback((index: number) => {
    setSavedImages(prev => prev.filter((_, i) => i !== index));
  }, []);

  const resolvedCourseId = useMemo(() => selectedCourseId ?? courseId, [selectedCourseId, courseId]);
  const isEditMode = useMemo(() => initialRating !== undefined || Boolean(initialContent), [initialRating, initialContent]);
  const modalTitle = useMemo(() => isEditMode ? '리뷰 수정' : '리뷰 작성', [isEditMode]);

  const handleSubmit = useCallback(() => {
    if (!resolvedCourseId) {
      alert('강좌를 선택해주세요.');
      return;
    }

    onSubmit({ 
      rating, 
      content: content.trim() || undefined, 
      files: uploadedFiles, 
      courseId: resolvedCourseId 
    });
    onClose();
  }, [resolvedCourseId, rating, content, uploadedFiles, onSubmit, onClose]);

  return (
    <Modal
      isOpen={isOpen}
      onClose={onClose}
      title={modalTitle}
      size="normal"
    >
      {courseTitle ? (
        <p className="review-modal-course-title">
          강좌: {courseTitle}
        </p>
      ) : availableCourses.length > 0 && (
        <div className="form-group form-group-spacing">
          <label className="form-label">강좌 선택 *</label>
          <Select
            value={selectedCourseId ? String(selectedCourseId) : ''}
            onValueChange={(value) => setSelectedCourseId(value ? Number(value) : undefined)}
            placeholder="강좌를 선택해주세요"
          >
            {availableCourses.map((course) => (
              <SelectItem key={course.id} value={String(course.id)}>
                {course.title} ({course.center.name})
              </SelectItem>
            ))}
          </Select>
        </div>
      )}

      <div className="form-group">
        <label className="form-label">평점</label>
        <div className="rating-input">
          {([1, 2, 3, 4, 5] as const).map((star) => (
            <span
              key={star}
              className={`rating-star ${star <= rating ? 'filled' : ''} cursor-pointer`}
              onClick={() => setRating(star)}
            >
              ★
            </span>
          ))}
        </div>
      </div>

      <div className="form-group">
        <label className="form-label" htmlFor="reviewContent">
          리뷰 내용
        </label>
        <textarea
          id="reviewContent"
          className="form-textarea"
          placeholder="리뷰를 작성해주세요..."
          value={content}
          onChange={(e) => setContent(e.target.value)}
        />
      </div>

      <div className="form-group">
        <label className="form-label">사진 첨부 (선택, 최대 3개)</label>
        <input
          type="file"
          id="reviewFiles"
          multiple
          accept="image/*"
          className="d-none"
          onChange={handleFileChange}
          disabled={totalImageCount >= MAX_IMAGE_COUNT}
        />
        <button
          type="button"
          className="btn btn-secondary"
          onClick={() => document.getElementById('reviewFiles')?.click()}
          disabled={totalImageCount >= MAX_IMAGE_COUNT}
        >
          {totalImageCount >= MAX_IMAGE_COUNT 
            ? '최대 개수 도달' 
            : `사진 선택 (${totalImageCount}/${MAX_IMAGE_COUNT})`}
        </button>
        {(savedImages.length > 0 || imagePreviews.length > 0) && (
          <div className="file-preview-container">
            <div className="file-preview-grid">
              {savedImages.map((imageUrl, idx) => (
                <div key={`saved-${idx}`} className="file-preview-item">
                  <img src={imageUrl} alt="기존 이미지" className="file-preview-img" />
                  <button
                    type="button"
                    className="file-remove-btn"
                    onClick={() => handleRemoveSavedImage(idx)}
                    aria-label="이미지 제거"
                  >
                    ×
                  </button>
                  <div className="file-name">기존 이미지</div>
                </div>
              ))}
              {imagePreviews.map((preview, idx) => (
                <div key={`new-${idx}`} className="file-preview-item">
                  <img src={preview} alt="미리보기" className="file-preview-img" />
                  <button
                    type="button"
                    className="file-remove-btn"
                    onClick={() => handleRemoveFile(idx)}
                    aria-label="파일 제거"
                  >
                    ×
                  </button>
                  <div className="file-name">{uploadedFiles[idx].name}</div>
                </div>
              ))}
            </div>
          </div>
        )}
      </div>

      <div className="modal-footer">
        <button className="btn btn-secondary" onClick={onClose}>
          취소
        </button>
        <button className="btn btn-primary" onClick={handleSubmit}>
          {isEditMode ? '수정' : '작성'}
        </button>
      </div>
    </Modal>
  );
};

interface ReviewModalState {
  isOpen: boolean;
  onSubmit: (data: ReviewFormData) => void;
  courseTitle?: string;
  courseId?: number;
  availableCourses?: Array<{ id: number; title: string; center: { name: string } }>;
  initialRating?: ReviewRating;
  initialContent?: string;
  initialImages?: string[];
}

export const useModalReview = () => {
  const [reviewState, setReviewState] = useState<ReviewModalState | null>(null);

  const review = (options: Omit<ReviewModalState, 'isOpen'>) => {
    setReviewState({ ...options, isOpen: true });
  };

  const ReviewModalComponent = () => {
    if (!reviewState) return null;

    const handleClose = () => {
      setReviewState(null);
    };

    const handleSubmit = (data: ReviewFormData) => {
      reviewState.onSubmit(data);
      handleClose();
    };

    return (
      <ReviewModal
        isOpen={reviewState.isOpen}
        onClose={handleClose}
        onSubmit={handleSubmit}
        courseTitle={reviewState.courseTitle}
        courseId={reviewState.courseId}
        availableCourses={reviewState.availableCourses || []}
        initialRating={reviewState.initialRating}
        initialContent={reviewState.initialContent}
        initialImages={reviewState.initialImages || []}
      />
    );
  };

  return { review, ReviewModal: ReviewModalComponent };
};
