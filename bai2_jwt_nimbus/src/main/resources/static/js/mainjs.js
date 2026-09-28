(() => {
  const tokenKey = 'jwt_token';
  const message = document.getElementById('message');
  const loginForm = document.getElementById('loginForm');

  if (loginForm) {
    loginForm.addEventListener('submit', async (event) => {
      event.preventDefault();
      if (message) message.textContent = 'Đang đăng nhập...';
      try {
        const response = await fetch('/auth/login', {
          method: 'POST', headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({ email: document.getElementById('email').value, password: document.getElementById('password').value })
        });
        const data = await response.json();
        if (!response.ok) throw new Error(data.error || 'Đăng nhập thất bại');
        localStorage.setItem(tokenKey, data.token);
        window.location.assign('/user/profile');
      } catch (error) { if (message) message.textContent = error.message; }
    });
  }

  const profile = document.getElementById('profile');
  if (profile) {
    const token = localStorage.getItem(tokenKey);
    if (!token) { window.location.replace('/login'); return; }
    fetch('/users/me', { headers: { Authorization: `Bearer ${token}` } })
      .then(async (response) => {
        const data = await response.json();
        if (!response.ok) throw new Error(data.error || 'Không thể tải hồ sơ');
        profile.replaceChildren();
        for (const [label, value] of [['Họ tên', data.fullName], ['Email', data.email]]) {
          const row = document.createElement('p');
          const title = document.createElement('strong'); title.textContent = `${label}: `;
          row.append(title, document.createTextNode(value || '')); profile.append(row);
        }
      })
      .catch((error) => {
        localStorage.removeItem(tokenKey);
        if (message) message.textContent = error.message;
        window.setTimeout(() => window.location.replace('/login'), 1000);
      });
  }

  const logout = document.getElementById('logoutButton');
  if (logout) logout.addEventListener('click', () => { localStorage.removeItem(tokenKey); window.location.replace('/login'); });
})();
