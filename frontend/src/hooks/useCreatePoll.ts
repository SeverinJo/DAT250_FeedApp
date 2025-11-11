import {type PollCreationRequest, PollEndpointsApi} from "../api/generated";
import {axiosInstance} from "../api/axiosInstance.ts";
import {useMutation, useQueryClient} from "@tanstack/react-query";


const api = new PollEndpointsApi(undefined, '', axiosInstance);

export function useCreatePoll() {
    const queryClient = useQueryClient();

    const { mutateAsync } = useMutation({
        mutationFn: async (req: PollCreationRequest) => {
            const res = await api.createPoll(req);

            if (res && res.status === 201) {
                await queryClient.invalidateQueries({ queryKey: ['polls'] });
                return true;
            }

            console.log("Failed to create new poll!");
            return false;
        }
    })

    return { createPoll: mutateAsync };
}