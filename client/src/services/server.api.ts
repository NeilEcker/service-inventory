import http from '@/http';

export default class ServerApiService {

    public static getServers() {
        return http.get('/server');
    }

    public static getServer(id: string) {
        return http.get(`/server/${id}`);
    }

    public static saveServer(server: any) {
        return http.post(`/server`, server);
    }

    public static updateServer(id: string, server: any) {
        return http.put(`/server/${id}`, server);
    }

    public static deleteServer(id: string) {
        return http.delete(`/server/${id}`);
    }

    public static getServersEndOfLife() {
        return http.get('/server/endOfLife');
    }

}
