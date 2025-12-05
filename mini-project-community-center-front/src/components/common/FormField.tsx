import type { ReactNode } from 'react';
import '@/styles/common/common.css';

interface FormFieldProps {
  label: string;
  children: ReactNode;
  className?: string;
}

export default function FormField({ label, children, className = '' }: FormFieldProps) {
  return (
    <div className={`form-group stacked ${className}`}>
      <label className="form-label">{label}</label>
      {children}
    </div>
  );
}
