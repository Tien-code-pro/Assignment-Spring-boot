<template>
  <div class="list row">
    <div class="col-md-12">
      <div class="col-md-8">
        <div class="input-group mb-3">
          <form action="/orders">
            <div>
              <input type="text" class="form-control" placeholder="Search by order id" name="orderId"
                     v-model="id"/>
            </div>
            <div class="input-group-append">
              <input type="text" class="form-control" placeholder="Search by name" name="nameUser"
                     v-model="fullName"/>
              <input type="text" class="form-control" placeholder="Search by phone" name="phone"
                     v-model="phone"/>
            </div>
            <div class="input-group-append">
              <input type="date" class="form-control" name="start"
                     v-model="start"/>
              <input type="date" class="form-control" name="end"
                     v-model="end"/>
            </div>
            <div class="input-group-append">
              <button class="btn btn-outline-secondary"
                      @click="searchTitle"
              >
                Search
              </button>
              <button class="btn btn-outline-secondary"
                      type="reset">
                Reset
              </button>
            </div>
          </form>
        </div>
      </div>
      <h4>Orders List</h4>
      <div>
        <router-link to="/" class="nav-link">List product</router-link>
      </div>
      <div>
        <table class="table table-striped table-bordered no-wrap">
          <thead>
          <tr>
            <th>ID</th>
            <th>Username</th>
            <th>Phone</th>
            <th>Price</th>
            <th>Created At</th>
            <th>Status</th>
            <th>Actions</th>
          </tr>
          </thead>
          <tbody>
          <tr :class="{ active: index === currentIndex }" v-for="order in orders"  v-bind:key="order.id">
            <td v-text="order.id"></td>
            <td v-text="order.user.fullName"></td>
            <td v-text="order.user.phone"></td>
            <td v-text="order.totalPrice"></td>
            <td v-text="order.createdAt"></td>
            <td v-text="order.status"></td>
            <td>Detail</td>
          </tr>
          </tbody>
        </table>
      </div>
      <div class="overflow-auto">
        <a-pagination
            :current="page"
            :page-size="limit"
            :total="totalPage"
            @change="handelClick"
            size="sm"
        ></a-pagination>
      </div>
    </div>
  </div>
</template>

<script>
import OrderDataService from "../service/OrderDataService";
// import axios from "axios";
// const API_URL = 'http://localhost:8080/api/v1/';

export default {
  name: "list-orders",
  data() {
    return {
      page: 1,
      limit: 5,
      totalPage: 0,
      currentPage: 1,
      orders: [],
      currentIndex: -1,
      id: "",
      totalPrice: "",
      fullName: "",
      phone: "",
      start: "",
      end: ""
    };
  },
  methods: {
    retrieveTutorials() {
      let urlParams = new URLSearchParams(window.location.search);
      if (urlParams.has("nameUser") || urlParams.has("orderId") || urlParams.has("phone" || urlParams.has("start" || urlParams.has("end")))) {
        // if (this.keyword === ""){
        this.nameUser = urlParams.get("nameUser");
        this.orderId = urlParams.get("orderId");
        this.phone = urlParams.get("phone");
        this.start = urlParams.get("start");
        this.end = urlParams.get("end");
        OrderDataService.getAllByName(this.page, this.limit, this.nameUser, this.orderId, this.phone, this.start, this.end)
            .then(response => {
              this.orders = response.data.content;
              this.page = response.data.currentPage;
              this.totalPage = response.data.totalPage;
              console.log(response.data);
            })
            .catch(e => {
              console.log(e);
            });
      } else {
      OrderDataService.getAllBy(this.page, this.limit)
          .then(response => {
            this.orders = response.data.content;
            this.page = response.data.currentPage;
            this.totalPage = response.data.totalPage;
            console.log(response.data);
          })
          .catch(e => {
            console.log(e);
          });
    }},
    handelClick(val){
      this.page = val;
      this.retrieveTutorials();
    },
    searchTitle() {
      this.page = 1;
      // TutorialDataService.findByTitle(this.title)
      //     .then(response => {
      //       this.tutorials = response.data;
      //       this.setActiveTutorial(null);
      //       console.log(response.data);
      //     })
      //     .catch(e => {
      //       console.log(e);
      //     });
    },
  },
  mounted() {
    let account = localStorage.getItem('account');
    console.log("KT account trong orderList: " + account)
    if( account ){
      window.axios = require('axios')
      window.axios.defaults.headers.common['Authorization'] = 'Bearer ' + account;
    }
    this.retrieveTutorials();
  },

};
</script>

<style>
.list {
  text-align: left;
  max-width: 750px;
  margin: auto;
}
</style>