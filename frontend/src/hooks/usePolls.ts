import {useQuery} from "@tanstack/react-query";
import {PollEndpointsApi} from "../api/generated";
import {axiosInstance} from "../api/axiosInstance.ts";

const api = new PollEndpointsApi(undefined, '', axiosInstance);

export function usePolls(onlyMyPolls: boolean) {

    const {data: polls, isLoading, isFetching} = useQuery({
        queryKey: ['polls', onlyMyPolls],
        queryFn: async () => {
            const response = await api.getPolls(onlyMyPolls);
            return response.data.sort((a,b) => (a.id ?? 0) - (b.id ?? 0)) ?? [];
        }
    });

    return {polls, isLoading, isFetching};
}
