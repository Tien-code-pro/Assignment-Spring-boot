import http from "../http-common";
class ProductDataService {
    getAllBy(page,limit) {
        return http.get(`/admin/products?page=${page}&limit=${limit}`);
    }
    getAllByName(page,limit,name) {
        return http.get(`/admin/products?page=${page}&limit=${limit}&name=${name}`);
    }
}
export default new ProductDataService();
