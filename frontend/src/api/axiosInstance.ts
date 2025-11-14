import axios from "axios";

const axiosInstance = axios.create({
    baseURL: "http://localhost:8080"
});

axiosInstance.interceptors.request.use((config) => {
   const token = localStorage.getItem("accessToken");
   if (token) {
       config.headers.Authorization = `Bearer ${token}`;
   }
   return config;
});

axiosInstance.interceptors.response.use(
    response => response,
    error => {
        const status = error.response?.status;
        const requestedUrl = error.config?.url;

        const isLogin = requestedUrl.includes("api/authenticate");

        if (status === 401 && !isLogin) {
            localStorage.removeItem("accessToken");
            window.location.reload();
        }
    }
);

export { axiosInstance };