import React, { useCallback, useEffect, useRef, useState } from 'react';
import * as SelectPrimitive from '@radix-ui/react-select';
import '@/styles/common/common.css';

interface SelectProps {
  value: string | undefined;
  onValueChange: (value: string) => void;
  children: React.ReactNode;
  placeholder?: string;
  className?: string;
}

interface SelectItemProps {
  value: string;
  children: React.ReactNode;
}

const ChevronDownIcon = () => (
  <svg width="16" height="16" viewBox="0 0 16 16" fill="none" xmlns="http://www.w3.org/2000/svg">
    <path d="M4 6L8 10L12 6" stroke="#475569" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" />
  </svg>
);

const scrollItemIntoView = (item: HTMLElement, viewport: HTMLDivElement) => {
  requestAnimationFrame(() => {
    const viewportRect = viewport.getBoundingClientRect();
    const itemRect = item.getBoundingClientRect();

    if (itemRect.top < viewportRect.top) {
      viewport.scrollTo({
        top: item.offsetTop - viewport.offsetTop - 8,
        behavior: 'smooth'
      });
    } else if (itemRect.bottom > viewportRect.bottom) {
      viewport.scrollTo({
        top: item.offsetTop - viewport.offsetTop - (viewport.clientHeight - item.offsetHeight) + 8,
        behavior: 'smooth'
      });
    }
  });
};

export function Select({ value, onValueChange, children, placeholder, className = '' }: SelectProps) {
  const scrollAnimationRef = useRef<number | null>(null);
  const viewportRef = useRef<HTMLDivElement | null>(null);
  const [isOpen, setIsOpen] = useState(false);

  const stopScrolling = useCallback(() => {
    if (scrollAnimationRef.current !== null) {
      cancelAnimationFrame(scrollAnimationRef.current);
      scrollAnimationRef.current = null;
    }
  }, []);

  const getViewport = useCallback((): HTMLDivElement | null => {
    if (viewportRef.current) {
      return viewportRef.current;
    }
    const viewport = document.querySelector('[data-radix-select-viewport]') as HTMLDivElement;
    if (viewport) {
      viewportRef.current = viewport;
    }
    return viewport;
  }, []);

  const startScrolling = useCallback((direction: 'up' | 'down') => {
    stopScrolling();

    const scroll = () => {
      const viewport = getViewport();
      if (!viewport) {
        stopScrolling();
        return;
      }

      const scrollSpeed = 150;
      
      if (direction === 'up') {
        const newScrollTop = Math.max(0, viewport.scrollTop - scrollSpeed);
        viewport.scrollTop = newScrollTop;
        if (newScrollTop <= 0) {
          stopScrolling();
          return;
        }
      } else {
        const maxScroll = viewport.scrollHeight - viewport.clientHeight;
        const newScrollTop = Math.min(maxScroll, viewport.scrollTop + scrollSpeed);
        viewport.scrollTop = newScrollTop;
        if (newScrollTop >= maxScroll) {
          stopScrolling();
          return;
        }
      }
      
      scrollAnimationRef.current = requestAnimationFrame(scroll);
    };

    scrollAnimationRef.current = requestAnimationFrame(scroll);
  }, [stopScrolling, getViewport]);

  const scrollToItem = useCallback((item: HTMLElement) => {
    const viewport = getViewport();
    if (!viewport || !item) return;
    scrollItemIntoView(item, viewport);
  }, [getViewport]);

  const scrollToSelectedItem = useCallback(() => {
    const viewport = getViewport();
    if (!viewport) return;

    const selectedItem = viewport.querySelector('[data-state="checked"]') as HTMLElement;
    if (selectedItem) {
      scrollToItem(selectedItem);
    }
  }, [getViewport, scrollToItem]);

  useEffect(() => {
    if (isOpen) {
      const timer = setTimeout(() => {
        const viewport = getViewport();
        if (viewport) {
          viewportRef.current = viewport;
          scrollToSelectedItem();
        }
      }, 50);

      const handleKeyDown = (e: KeyboardEvent) => {
        if (e.key === 'ArrowUp' || e.key === 'ArrowDown') {
          setTimeout(() => {
            const viewport = getViewport();
            if (!viewport) return;

            const highlightedItem = viewport.querySelector('[data-highlighted]') as HTMLElement;
            if (highlightedItem) {
              scrollItemIntoView(highlightedItem, viewport);
            }
          }, 0);
        }
      };

      document.addEventListener('keydown', handleKeyDown);

      return () => {
        clearTimeout(timer);
        stopScrolling();
        document.removeEventListener('keydown', handleKeyDown);
      };
    } else {
      stopScrolling();
      viewportRef.current = null;
    }
  }, [isOpen, stopScrolling, getViewport, scrollToSelectedItem]);

  const handleValueChange = (newValue: string) => {
    onValueChange(newValue === '__empty__' ? '' : (newValue || ''));
  };

  const rootValue = (value === undefined || value === '') ? '__empty__' : value;

  return (
    <SelectPrimitive.Root
      value={rootValue}
      onValueChange={handleValueChange}
      onOpenChange={setIsOpen}
    >
      <SelectPrimitive.Trigger className={`form-select select-trigger ${className}`}>
        {rootValue === '__empty__' && placeholder ? (
          <SelectPrimitive.Value placeholder={placeholder}>
            <span className="select-placeholder">{placeholder}</span>
          </SelectPrimitive.Value>
        ) : (
          <SelectPrimitive.Value />
        )}
        <SelectPrimitive.Icon className="select-icon">
          <ChevronDownIcon />
        </SelectPrimitive.Icon>
      </SelectPrimitive.Trigger>
      <SelectPrimitive.Portal>
        <SelectPrimitive.Content className="select-content" position="popper" sideOffset={8}>
          <SelectPrimitive.ScrollUpButton 
            className="select-scroll-button"
            onMouseEnter={() => startScrolling('up')}
            onMouseLeave={stopScrolling}
          >
            <svg width="16" height="16" viewBox="0 0 16 16" fill="none">
              <path d="M8 4L4 8H12L8 4Z" fill="#475569" />
            </svg>
          </SelectPrimitive.ScrollUpButton>
          <SelectPrimitive.Viewport 
            className="select-viewport"
            onDragStart={(e) => {
              e.preventDefault();
            }}
          >
            {placeholder && (
              <SelectPrimitive.Item value="__empty__" className="select-item d-none">
                <SelectPrimitive.ItemText>{placeholder}</SelectPrimitive.ItemText>
              </SelectPrimitive.Item>
            )}
            {children}
          </SelectPrimitive.Viewport>
          <SelectPrimitive.ScrollDownButton 
            className="select-scroll-button"
            onMouseEnter={() => startScrolling('down')}
            onMouseLeave={stopScrolling}
          >
            <svg width="16" height="16" viewBox="0 0 16 16" fill="none">
              <path d="M8 12L12 8H4L8 12Z" fill="#475569" />
            </svg>
          </SelectPrimitive.ScrollDownButton>
        </SelectPrimitive.Content>
      </SelectPrimitive.Portal>
    </SelectPrimitive.Root>
  );
}

export function SelectItem({ value, children }: SelectItemProps) {
  const itemValue = value === '' ? '__empty__' : value;

  const handleMouseEnter = useCallback((e: React.MouseEvent<HTMLDivElement>) => {
    const item = e.currentTarget;
    if (!item) return;

    const viewport = item.closest('[data-radix-select-viewport]') as HTMLDivElement;
    if (viewport) {
      scrollItemIntoView(item, viewport);
    }
  }, []);

  return (
    <SelectPrimitive.Item 
      value={itemValue} 
      className="select-item"
      onMouseEnter={handleMouseEnter}
      onDragStart={(e) => {
        e.preventDefault();
      }}
    >
      <SelectPrimitive.ItemText>{children}</SelectPrimitive.ItemText>
    </SelectPrimitive.Item>
  );
}
