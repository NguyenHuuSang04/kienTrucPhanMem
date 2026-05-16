#!/bin/sh
# Seed Redis với 10 sản phẩm + stock=100 mỗi sản phẩm.
# Idempotent: nếu products:all đã có data thì skip.
set -e

REDIS_HOST="${REDIS_HOST:-redis}"
REDIS_PORT="${REDIS_PORT:-6379}"

echo "Seeding Redis at ${REDIS_HOST}:${REDIS_PORT}..."

EXISTING=$(redis-cli -h "$REDIS_HOST" -p "$REDIS_PORT" HLEN products:all)

if [ "$EXISTING" -ge "10" ]; then
  echo "Already seeded ($EXISTING products). Skipping."
  exit 0
fi

# Lưu product dưới dạng JSON serialized cho Spring Boot Jackson đọc lại được.
# Schema phải khớp với class Product (id, name, price, image, description) + @class hint.

CLS='vn.iuh.flashsale.product.model.Product'

set_product() {
  ID="$1"
  NAME="$2"
  PRICE="$3"
  IMAGE="$4"
  DESC="$5"
  JSON="{\"@class\":\"${CLS}\",\"id\":\"${ID}\",\"name\":\"${NAME}\",\"price\":${PRICE},\"image\":\"${IMAGE}\",\"description\":\"${DESC}\"}"
  redis-cli -h "$REDIS_HOST" -p "$REDIS_PORT" HSET products:all "$ID" "$JSON" > /dev/null
  redis-cli -h "$REDIS_HOST" -p "$REDIS_PORT" SET "stock:${ID}" 100 > /dev/null
}

set_product "p001" "iPhone 16 Pro Max" 32990000 "https://images.unsplash.com/photo-1696446702183-be0eb4f1fbb6?w=800" "Flash Sale - giam 5 trieu, so luong co han."
set_product "p002" "MacBook Air M3" 27990000 "https://images.unsplash.com/photo-1517336714731-489689fd1ca8?w=800" "Air mong nhe, M3 manh me."
set_product "p003" "AirPods Pro 2" 5490000 "https://images.unsplash.com/photo-1606220588913-b3aacb4d2f46?w=800" "Chong on chu dong, USB-C."
set_product "p004" "iPad Pro 11" 22990000 "https://images.unsplash.com/photo-1544244015-0df4b3ffc6b0?w=800" "Liquid Retina XDR."
set_product "p005" "Apple Watch Ultra 2" 19990000 "https://images.unsplash.com/photo-1546868871-7041f2a55e12?w=800" "GPS chinh xac, ben bi."
set_product "p006" "Sony WH-1000XM5" 7990000 "https://images.unsplash.com/photo-1590658268037-6bf12165a8df?w=800" "Tai nghe chong on flagship."
set_product "p007" "Samsung Galaxy S25" 24990000 "https://images.unsplash.com/photo-1610945415295-d9bbf067e59c?w=800" "AI native, camera 200MP."
set_product "p008" "Logitech MX Master 3S" 2290000 "https://images.unsplash.com/photo-1527864550417-7fd91fc51a46?w=800" "Chuot nang suat cao cap."
set_product "p009" "Dell UltraSharp U2725QE" 17990000 "https://images.unsplash.com/photo-1527443224154-c4a3942d3acf?w=800" "4K, USB-C 90W."
set_product "p010" "Keychron K2 Pro" 2790000 "https://images.unsplash.com/photo-1587829741301-dc798b83add3?w=800" "Ban phim co Bluetooth."

echo "Seeded 10 products. stock=100 each."
