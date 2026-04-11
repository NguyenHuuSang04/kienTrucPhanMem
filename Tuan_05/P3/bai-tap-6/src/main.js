import { sum } from "./math.js";

const app = document.getElementById("app");

app.innerHTML = `
  <h1>Bai tap 6 - CI/CD voi Docker Compose</h1>
  <p>Development mode: app service mount code tu host.</p>
  <p>Unit test sample: sum(2, 3) = <strong>${sum(2, 3)}</strong></p>
`;