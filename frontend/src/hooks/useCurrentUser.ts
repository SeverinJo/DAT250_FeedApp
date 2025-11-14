import {useQuery} from "@tanstack/react-query";
import {UserEndpointsApi} from "../api/generated";
import {axiosInstance} from "../api/axiosInstance.ts";

const api = new UserEndpointsApi(undefined, '', axiosInstance);

export function useCurrentUser() {

    const {data: isAuthenticated, isLoading: authLoading, isFetching: authFetching} = useQuery<boolean, Error>({
        queryKey: ['currentUser'],
        queryFn: async () => {
            const token = localStorage.getItem("accessToken");
            return !!token;
        },
        placeholderData: (prev) => prev
    });

    const {data: userInfo, isLoading: userLoading, isFetching: userFetching} = useQuery({
        queryKey: ["currentUserInfo"],
        queryFn: async () => {
            const response = await api.getUserInformation();
            return response.data;
        },
        enabled: !!isAuthenticated,
    });

    const isLoading = authLoading || userLoading || userFetching || authFetching;
    return { isAuthenticated, userInfo, isLoading };
}
