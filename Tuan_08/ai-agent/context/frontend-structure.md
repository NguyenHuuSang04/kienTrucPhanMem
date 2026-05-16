# Frontend Structure

```
frontend/
├── public/
├── src/
│   ├── api/
│   │   ├── client.ts            # axios factory chung (timeout 5s, JSON)
│   │   ├── productApi.ts        # baseURL = VITE_PU1_URL
│   │   ├── cartApi.ts           # VITE_PU2_URL
│   │   ├── orderApi.ts          # VITE_PU3_URL
│   │   └── stockApi.ts          # VITE_PU4_URL
│   ├── components/
│   │   ├── Navbar.tsx           # global-nav đen 44px (DESIGN.md)
│   │   ├── ProductTile.tsx      # alternating light/dark theo index
│   │   ├── ButtonPrimary.tsx    # blue pill (rounded-pill)
│   │   └── ButtonDarkUtility.tsx
│   ├── pages/
│   │   ├── ProductsPage.tsx     # GET /products, render grid
│   │   ├── ProductDetailPage.tsx# GET /products/:id + poll /stock/:id
│   │   ├── CartPage.tsx         # GET /cart, button Checkout
│   │   └── OrderSuccessPage.tsx # hiển thị order vừa tạo
│   ├── theme/
│   │   ├── tokens.css           # CSS variables từ DESIGN.md
│   │   └── apple.css            # Tailwind @theme + utility classes
│   ├── hooks/
│   │   └── usePollStock.ts      # poll /stock/:id mỗi 2s
│   ├── types/
│   │   └── index.ts             # IProduct, ICart, IOrder
│   ├── App.tsx                  # router + Navbar
│   ├── main.tsx
│   └── index.css                # @import "tailwindcss"; + @import tokens.css
├── .env                         # VITE_PU1_URL=http://192.168.137.157:8081 ...
├── index.html
├── package.json
├── tsconfig.json
└── vite.config.ts               # server.host=0.0.0.0, port=3000, plugin Tailwind
```

## Quy ước

- **Mỗi PU 1 axios instance** trong `api/`. Không share base URL — IP có thể đổi mà không ảnh hưởng tới các PU khác.
- **userId hardcode = "u1"** ở phase MVP (không có auth).
- **Poll stock mỗi 2 giây** trên ProductDetailPage để demo "real-time".
- **Tailwind v4 chỉ dùng @theme directive** trong `theme/apple.css`, không dùng `tailwind.config.js` (Tailwind 4 ưu tiên CSS-first config).
- **Không Shadcn**. DESIGN.md hướng tới photography-first, các Shadcn primitive sẽ thừa.
