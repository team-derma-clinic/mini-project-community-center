import { type InputHTMLAttributes, forwardRef } from 'react';
import '@/styles/common/common.css';
import FormField from '@/components/common/FormField';

export interface InputProps extends InputHTMLAttributes<HTMLInputElement> {
  label?: string;
  error?: string;
  helperText?: string;
  fullWidth?: boolean;
}

const Input = forwardRef<HTMLInputElement, InputProps>(
  ({ label, error, helperText, fullWidth = true, className = '', ...props }, ref) => {
    const inputClasses = [
      'form-input',
      error ? 'form-input-error' : '',
      className
    ].filter(Boolean).join(' ');

    const inputElement = (
      <>
        <input
          ref={ref}
          className={inputClasses}
          aria-invalid={error ? 'true' : 'false'}
          aria-describedby={error ? `${props.id || 'input'}-error` : undefined}
          {...props}
        />
        {error && (
          <span id={`${props.id || 'input'}-error`} className="form-error" role="alert">
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
          {inputElement}
        </FormField>
      );
    }

    return inputElement;
  }
);

Input.displayName = 'Input';

export default Input;

