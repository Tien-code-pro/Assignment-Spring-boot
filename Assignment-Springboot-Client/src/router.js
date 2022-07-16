import { createWebHistory, createRouter } from "vue-router";
const routes =  [
    {
        path: "/",
        alias: "/admin/products",
        name: "products",
        component: () => import("./components/ListProduct")
    },
    {
        path: "/orders",
        alias: "/admin/orders",
        name: "orders",
        component: () => import("./components/ListOrder")
    },
    {
        path: "/register",
        alias: "/register",
        name: "register",
        component: () => import("./components/Register")
    },
    {
        path: "/login",
        alias: "/login",
        name: "login",
        component: () => import("./components/Login")
    }
];
const router = createRouter({
    history: createWebHistory(),
    routes,
});
export default router;
