import { useEffect, useState } from "react";
import { productApi } from "../api/productApi";
import type { IProduct } from "../types";
import ProductTile from "../components/ProductTile";

const variants = ["light", "dark", "parchment", "dark"] as const;

export default function ProductsPage() {
  const [products, setProducts] = useState<IProduct[]>([]);
  const [error, setError] = useState<string | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    productApi
      .listAll()
      .then(setProducts)
      .catch((e) => setError(e?.message ?? "Lỗi tải sản phẩm"))
      .finally(() => setLoading(false));
  }, []);

  if (loading) {
    return (
      <section className="product-tile-light text-center">
        <p className="text-lead opacity-60">Đang tải sản phẩm...</p>
      </section>
    );
  }

  if (error) {
    return (
      <section className="product-tile-light text-center">
        <h2 className="text-display-lg font-display mb-4">
          Không kết nối được PU1 (Product Service)
        </h2>
        <p className="text-body-apple opacity-60">{error}</p>
        <p className="text-caption opacity-50 mt-4">
          Yêu cầu: Huy đã chạy PU1 ở {import.meta.env.VITE_PU1_URL}, Sang đã
          chạy Redis.
        </p>
      </section>
    );
  }

  if (products.length === 0) {
    return (
      <section className="product-tile-light text-center">
        <h2 className="text-display-lg font-display">Chưa có sản phẩm</h2>
        <p className="text-body-apple opacity-60 mt-4">
          Hãy seed data Redis: chạy{" "}
          <code>curl -X POST {import.meta.env.VITE_PU1_URL}/admin/seed</code>
        </p>
      </section>
    );
  }

  return (
    <main>
      <section className="product-tile-light text-center pt-20 pb-10">
        <h1 className="text-hero-display font-display">Flash Sale</h1>
        <p className="text-lead opacity-60 mt-4">
          Đặt hàng ngay. Tồn kho cập nhật real-time.
        </p>
      </section>
      {products.map((p, idx) => (
        <ProductTile
          key={p.id}
          product={p}
          variant={variants[idx % variants.length]}
        />
      ))}
    </main>
  );
}
