import '@/styles/common/common.css';
import type { ReactNode } from 'react';

export interface BadgeProps {
  variant?: 'default' | 'success' | 'warning' | 'danger' | 'info';
  size?: 'small' | 'medium' | 'large';
  children: ReactNode;
  onRemove?: () => void;
  className?: string;
}

export default function Badge({
  variant = 'default',
  size = 'medium',
  children,
  onRemove,
  className = ''
}: BadgeProps) {
  const baseClass = 'badge';
  const variantClass = `badge-${variant}`;
  const sizeClass = size !== 'medium' ? `badge-${size}` : '';
  const removableClass = onRemove ? 'badge-removable' : '';

  const classes = [
    baseClass,
    variantClass,
    sizeClass,
    removableClass,
    className
  ].filter(Boolean).join(' ');

  return (
    <span className={classes}>
      <span className="badge-content">{children}</span>
      {onRemove && (
        <button
          type="button"
          className="badge-remove"
          onClick={onRemove}
          aria-label="제거"
        >
          ×
        </button>
      )}
    </span>
  );
}

