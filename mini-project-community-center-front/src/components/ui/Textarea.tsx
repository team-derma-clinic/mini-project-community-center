import { forwardRef, type TextareaHTMLAttributes } from 'react';
import FormField from '@/components/common/FormField';
import '@/styles/common/common.css';

export interface TextareaProps extends TextareaHTMLAttributes<HTMLTextAreaElement> {
  label?: string;
  error?: string;
  helperText?: string;
  fullWidth?: boolean;
  rows?: number;
}

const Textarea = forwardRef<HTMLTextAreaElement, TextareaProps>(
  ({ label, error, helperText, fullWidth = true, rows = 4, className = '', ...props }, ref) => {
    const textareaClasses = [
      'form-textarea',
      error ? 'form-textarea-error' : '',
      className
    ].filter(Boolean).join(' ');

    const textareaElement = (
      <>
        <textarea
          ref={ref}
          className={textareaClasses}
          rows={rows}
          aria-invalid={error ? 'true' : 'false'}
          aria-describedby={error ? `${props.id || 'textarea'}-error` : undefined}
          {...props}
        />
        {error && (
          <span id={`${props.id || 'textarea'}-error`} className="form-error" role="alert">
            {error}
          </span>
        )}
        {helperText && !error && (
          <span className="form-helper-text">{helperText}</span>
        )}
      </>
    );

    if (label) {
      return (
        <FormField label={label} className={fullWidth ? 'form-field-full-width' : ''}>
          {textareaElement}
        </FormField>
      );
    }

    return textareaElement;
  }
);

Textarea.displayName = 'Textarea';

export default Textarea;

