import {useMutation, useQueryClient} from "@tanstack/react-query";
import {AuthenticationEndpointsApi, type AuthenticationRequest} from "../api/generated";
import {axiosInstance} from "../api/axiosInstance.ts";


const api = new AuthenticationEndpointsApi(undefined, '', axiosInstance);

export function useLogin() {
    const queryClient = useQueryClient();

    const { mutateAsync } = useMutation({
        mutationFn: async (req: AuthenticationRequest) => {
            const res = await api.authenticate(req);

            if (res && res.status === 200) {
                localStorage.setItem('accessToken', res.data.accessToken ?? '');
                await queryClient.invalidateQueries({ queryKey: ['currentUser']})
                return true;
            }

            console.log("Login failed. Invalid username or password!")
            return false;
        }
    });

    return { login: mutateAsync };
}