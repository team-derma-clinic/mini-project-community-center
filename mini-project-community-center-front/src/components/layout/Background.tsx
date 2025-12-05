import '@/styles/common/background.css';

interface BackgroundProps {
  variant?: "dafault" | "login";
  children?: React.ReactNode;
}

export default function Background({
  variant = "dafault",
  children,
}: BackgroundProps) {
  return (
    <div className={`background-wrapper background-${variant}`}>{children}</div>
  );
}
