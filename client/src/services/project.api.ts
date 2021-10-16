import http from '@/http';

export default class ProjectApiService {

    public static getProject(id: string) {
        return http.get(`/project/${id}`);
    }

    public static getProjects() {
        return http.get('/project');
    }

    public static saveProject(project: any) {
        return http.post(`/project`, project);
    }

    public static updateProject(id: string, project: any) {
        return http.put(`/project/${id}`, project);
    }

    public static deleteProject(id: string) {
        return http.delete(`/project/${id}`);
    }

    public static getProjectTypes() {
        return http.get(`/projectType`);
    }

    public static gantt() {
        return http.get(`/project/gantt`);
    }

    public static getMyCurrentProjects() {
        return http.get(`/project/myCurrentProjects`);
    }
}
