import http from "../http-common";
class OrderDataService {
    getAllBy(page,limit) {
        return http.get(`/admin/orders?page=${page}&limit=${limit}`);
    }
    getAllByName(page,limit,nameUser,orderId,phone,start, end) {
        return http.get(`/admin/orders?page=${page}&limit=${limit}&nameUser=${nameUser}&orderId=${orderId}&phone=${phone}&start=${start}&end=${end}`);
    }
}
export default new OrderDataService();
