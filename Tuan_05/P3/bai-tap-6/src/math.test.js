import { describe, expect, it } from "vitest";
import { sum } from "./math.js";

describe("sum", () => {
    it("adds two positive numbers", () => {
        expect(sum(2, 3)).toBe(5);
    });

    it("adds negative numbers", () => {
        expect(sum(-1, -4)).toBe(-5);
    });
});