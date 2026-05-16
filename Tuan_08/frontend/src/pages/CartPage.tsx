import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { cartApi } from "../api/cartApi";
import { orderApi } from "../api/orderApi";
import { productApi } from "../api/productApi";
import type { ICart, IProduct } from "../types";

export default function CartPage() {
  const navigate = useNavigate();
  const [cart, setCart] = useState<ICart | null>(null);
  const [products, setProducts] = useState<Record<string, IProduct>>({});
  const [error, setError] = useState<string | null>(null);
  const [submitting, setSubmitting] = useState(false);

  const loadCart = async () => {
    try {
      const c = await cartApi.get();
      setCart(c);
      const ids = Object.keys(c.items ?? {});
      if (ids.length > 0) {
        const list = await productApi.listAll();
        const byId: Record<string, IProduct> = {};
        list.forEach((p) => (byId[p.id] = p));
        setProducts(byId);
      }
    } catch (e) {
      setError((e as Error).message);
    }
  };

  useEffect(() => {
    loadCart();
  }, []);

  const handleCheckout = async () => {
    setSubmitting(true);
    setError(null);
    try {
      const order = await orderApi.checkout();
      navigate(`/order-success`, { state: order });
    } catch (e) {
      const msg =
        (e as { response?: { data?: { error?: string } } })?.response?.data
          ?.error ?? (e as Error).message;
      setError(msg);
    } finally {
      setSubmitting(false);
    }
  };

  if (error)
    return (
      <section className="product-tile-light text-center">
        <h2 className="text-display-lg font-display mb-4">Lỗi</h2>
        <p className="text-body-apple opacity-70">{error}</p>
        <button
          className="btn-secondary-pill mt-6"
          onClick={() => navigate("/")}
        >
          Về trang chủ
        </button>
      </section>
    );

  if (!cart)
    return (
      <section className="product-tile-light text-center">
        <p className="text-lead opacity-60">Đang tải giỏ hàng...</p>
      </section>
    );

  const items = Object.entries(cart.items ?? {});
  const total = items.reduce((sum, [pid, qty]) => {
    const p = products[pid];
    return sum + (p?.price ?? 0) * Number(qty);
  }, 0);

  return (
    <main className="product-tile-light min-h-screen">
      <div className="max-w-3xl mx-auto">
        <h1 className="text-hero-display font-display text-center mb-12">
          Giỏ hàng
        </h1>
        {items.length === 0 ? (
          <p className="text-lead opacity-60 text-center">Giỏ hàng trống.</p>
        ) : (
          <>
            <div className="space-y-4">
              {items.map(([pid, qty]) => {
                const p = products[pid];
                return (
                  <div
                    key={pid}
                    className="utility-card flex items-center justify-between"
                  >
                    <div>
                      <p className="text-body-apple font-display">
                        {p?.name ?? pid}
                      </p>
                      <p className="text-caption opacity-60">
                        Số lượng: {qty}
                      </p>
                    </div>
                    <p className="text-tagline font-display">
                      {((p?.price ?? 0) * Number(qty)).toLocaleString("vi-VN")} ₫
                    </p>
                  </div>
                );
              })}
            </div>
            <div className="flex items-center justify-between mt-10">
              <span className="text-tagline">Tổng cộng</span>
              <span className="text-display-lg font-display">
                {total.toLocaleString("vi-VN")} ₫
              </span>
            </div>
            <div className="flex justify-center gap-3 mt-10">
              <button
                type="button"
                onClick={handleCheckout}
                disabled={submitting}
                className="btn-primary"
              >
                {submitting ? "Đang đặt hàng..." : "Đặt hàng"}
              </button>
              <button
                type="button"
                onClick={() => navigate("/")}
                className="btn-secondary-pill"
              >
                Tiếp tục mua sắm
              </button>
            </div>
          </>
        )}
      </div>
    </main>
  );
}
