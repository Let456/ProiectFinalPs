import { RouteObject, createBrowserRouter } from "react-router-dom";
import Login from "../Pages/Login";
import Admin, { Administrator } from "../Pages/Administrator";
import Angajat from "../Pages/Angajat";
import Manager from "../Pages/Manager";

const routes: RouteObject[] = [
    {
        path: "/",
        element: <Login />
    },

    {
        path: "/Administrator",
        element: <Administrator />
    },
    {
        path: "/Angajat",
        element: <Angajat />
    },
    {
        path: "/Manager",
        element: <Manager />
    },
];

export const router = createBrowserRouter(routes)