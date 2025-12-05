import { Link, NavLink, useNavigate } from 'react-router-dom';
import { useEffect, useState, useMemo, useCallback } from 'react';
import * as DropdownMenu from '@radix-ui/react-dropdown-menu';
import { useModalConfirm } from '@/components/ui/Modal';
import Background from '@/components/layout/Background';
import Footer from '@/components/layout/Footer';
import '@/styles/common/layout.css';

interface NavItem {
  path: string;
  label: string;
  submenu?: Array<{ path: string; label: string; submenu?: Array<{ path: string; label: string }> }>;
}

interface LayoutProps {
  children: React.ReactNode;
  role?: 'STUDENT' | 'INSTRUCTOR' | 'STAFF' | 'ADMIN';
  userName?: string;
  navItems?: NavItem[];
}

const ARROW_PATHS = {
  down: 'M3 5L6 8L9 5',
  right: 'M5 4L8 7L5 10'
};

const DropdownArrowIcon = ({ direction = 'down' }: { direction?: 'down' | 'right' }) => (
  <svg
    className={direction === 'right' ? 'nested-arrow' : 'dropdown-arrow'}
    width="12"
    height="12"
    viewBox="0 0 12 12"
    aria-hidden="true"
    focusable="false"
  >
    <path d={ARROW_PATHS[direction]} stroke="currentColor" strokeWidth="2" fill="none" />
  </svg>
);

export default function Layout({ children, role = 'STUDENT', userName, navItems }: LayoutProps) {
  const navigate = useNavigate();
  const { confirm, ConfirmModal } = useModalConfirm();
  const [userDisplayName, setUserDisplayName] = useState<string>('');

  useEffect(() => {
    if (userName) {
      setUserDisplayName(userName);
      return;
    }

    try {
      const savedUser = localStorage.getItem('user');
      if (savedUser) {
        const user = JSON.parse(savedUser);
        setUserDisplayName(user.name || user.loginId || '');
      }
    } catch {
      setUserDisplayName('');
    }
  }, [userName]);

  const getRoleDashboardPath = useCallback((userRole: typeof role) => {
    const paths = {
      STUDENT: '/user',
      ADMIN: '/admin/dashboard',
      INSTRUCTOR: '/instructor/dashboard',
      STAFF: '/staff/dashboard'
    };
    return paths[userRole] || '/user';
  }, []);

  const getRoleLabel = useCallback((userRole: typeof role) => {
    const labels: Record<typeof role, string> = {
      STUDENT: '',
      ADMIN: '관리자',
      INSTRUCTOR: '강사',
      STAFF: '스태프'
    };
    return labels[userRole] || '';
  }, []);

  const defaultNavItems: Record<'STUDENT' | 'INSTRUCTOR' | 'STAFF' | 'ADMIN', NavItem[]> = useMemo(() => ({
    STUDENT: [
      { path: '/user/centers', label: '소개' },
      { path: '/user', label: '수강신청' },
      { path: '/user/enrollments/pending', label: '신청현황' },
      {
        path: '#',
        label: '나의 활동',
        submenu: [
          { path: '/user/enrollments', label: '수강내역' },
          { path: '/user/reviews', label: '리뷰' },
          { path: '/user/attendance', label: '출석' }
        ]
      },
      { path: '/user/mypage', label: '마이페이지' }
    ],
    INSTRUCTOR: [
      { path: '/instructor/dashboard', label: '대시보드' },
      { path: '/instructor/courses', label: '강좌 관리' },
      {
        path: '#',
        label: '출석 관리',
        submenu: [
          { path: '/instructor/attendance/register', label: '출석 등록' },
          { path: '/instructor/attendance/update', label: '출석 수정' }
        ]
      },
      { path: '/instructor/reviews', label: '리뷰' },
      { path: '/instructor/mypage', label: '마이페이지' }
    ],
    STAFF: [
      {
        path: '#',
        label: '관리',
        submenu: [
          { path: '/staff/centers', label: '센터 관리' },
          { path: '/staff/courses', label: '강좌 · 세션 관리' },
          {
            path: '#',
            label: '등록',
            submenu: [
              { path: '/staff/enrollments', label: '수강 등록' },
              { path: '/staff/payments', label: '결제' }
            ]
          },
          {
            path: '#',
            label: '회원 관리',
            submenu: [
              { path: '/staff/users/attendance', label: '출석' },
              { path: '/staff/users/reviews', label: '리뷰' }
            ]
          }
        ]
      },
      {
        path: '#',
        label: '통계 · 리포트',
        submenu: [
          { path: '/staff/dashboard', label: '대시보드' },
          { path: '/staff/reports/course', label: '강좌별' },
          { path: '/staff/reports/attendance', label: '출석률' },
          { path: '/staff/reports/category', label: '카테고리별' },
          { path: '/staff/reports/instructor', label: '강사별' }
        ]
      },
      { path: '/staff/mypage', label: '마이페이지' }
    ],
    ADMIN: [
      {
        path: '#',
        label: '관리',
        submenu: [
          { path: '/admin/centers', label: '센터 관리' },
          { path: '/admin/courses', label: '강좌 · 세션 관리' },
          {
            path: '#',
            label: '등록',
            submenu: [
              { path: '/admin/enrollments', label: '수강 등록' },
              { path: '/admin/payments', label: '결제' }
            ]
          },
          {
            path: '#',
            label: '회원 관리',
            submenu: [
              { path: '/admin/users/attendance', label: '출석' },
              { path: '/admin/users/reviews', label: '리뷰' }
            ]
          },
          { path: '/admin/users/roles', label: '사용자 · 역할 관리' }
        ]
      },
      {
        path: '#',
        label: '통계 · 리포트',
        submenu: [
          { path: '/admin/dashboard', label: '대시보드' },
          { path: '/admin/reports/course', label: '강좌별' },
          { path: '/admin/reports/attendance', label: '출석률' },
          { path: '/admin/reports/category', label: '카테고리별' },
          { path: '/admin/reports/instructor', label: '강사별' }
        ]
      },
      {
        path: '#',
        label: '시스템 관리',
        submenu: [
          { path: '/admin/system/health', label: '시스템 상태' }
        ]
      },
      { path: '/admin/mypage', label: '마이페이지' }
    ]
  }), []);

  const items = useMemo(
    () => navItems || defaultNavItems[role] || [],
    [navItems, defaultNavItems, role]
  );

  const handleLogout = useCallback(() => {
    confirm({
      title: '로그아웃',
      message: '정말 로그아웃하시겠습니까?',
      onConfirm: () => {
        localStorage.removeItem('token');
        localStorage.removeItem('user');
        navigate('/login');
      },
    });
  }, [confirm, navigate]);

  const renderNavItem = useCallback((item: NavItem, depth = 0, index?: number) => {
    const itemKey = item.path === '#' ? `${item.label}-${index}` : item.path;
    
    if (item.submenu && item.submenu.length > 0) {
      return (
        <DropdownMenu.Root key={itemKey}>
          <DropdownMenu.Trigger asChild>
            <a
              href="#"
              className="nav-link has-submenu"
              onClick={(e) => e.preventDefault()}
            >
              {item.label}
              <DropdownArrowIcon direction={depth === 0 ? 'down' : 'right'} />
            </a>
          </DropdownMenu.Trigger>
          <DropdownMenu.Portal>
            <DropdownMenu.Content className="dropdown-content" sideOffset={5}>
              {item.submenu.map((subItem, subIdx) => {
                const subKey = subItem.path === '#' ? `${subItem.label}-${subIdx}` : subItem.path;
                return (
                  <div key={subKey}>
                    {subItem.submenu && subItem.submenu.length > 0 ? (
                      <DropdownMenu.Sub>
                        <DropdownMenu.SubTrigger className="dropdown-item nested-trigger">
                          {subItem.label}
                          <DropdownArrowIcon direction="right" />
                        </DropdownMenu.SubTrigger>
                        <DropdownMenu.Portal>
                          <DropdownMenu.SubContent className="dropdown-content nested-content" sideOffset={5}>
                            {subItem.submenu.map((nestedItem, nestedIdx) => {
                              const nestedKey = nestedItem.path === '#' ? `${nestedItem.label}-${nestedIdx}` : nestedItem.path;
                              return (
                                <DropdownMenu.Item
                                  key={nestedKey}
                                  className="dropdown-item"
                                  onSelect={() => navigate(nestedItem.path)}
                                >
                                  {nestedItem.label}
                                </DropdownMenu.Item>
                              );
                            })}
                          </DropdownMenu.SubContent>
                        </DropdownMenu.Portal>
                      </DropdownMenu.Sub>
                    ) : (
                      <DropdownMenu.Item
                        key={subKey}
                        className="dropdown-item"
                        onSelect={() => navigate(subItem.path)}
                      >
                        {subItem.label}
                      </DropdownMenu.Item>
                    )}
                  </div>
                );
              })}
            </DropdownMenu.Content>
          </DropdownMenu.Portal>
        </DropdownMenu.Root>
      );
    }

    return (
      <NavLink
        key={itemKey}
        to={item.path}
        end
        className={({ isActive }) => `nav-link${isActive ? ' active' : ''}`}
      >
        {item.label}
      </NavLink>
    );
  }, [navigate]);

  return (
    <Background>
      <ConfirmModal />
      <div className="layout">
        <header className="header">
          <div className="container">
            <Link to={getRoleDashboardPath(role)} className="logo">
              {role === 'STUDENT' ? '시민문화센터' : `시민문화센터 - ${getRoleLabel(role)}`}
            </Link>
            <nav className="nav">
              {items.map((item, index) => renderNavItem(item, 0, index))}
            </nav>
            <div className="user-menu">
              {userDisplayName && <span className="user-name">{userDisplayName}님</span>}
              <button className="btn btn-secondary" onClick={handleLogout}>
                로그아웃
              </button>
            </div>
          </div>
        </header>
        <main className="main-content">
          <div className="container">
            {children}
          </div>
        </main>
        <Footer />
      </div>
    </Background>
  );
}
