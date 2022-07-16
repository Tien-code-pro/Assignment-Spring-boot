<template>
  <div class="row">
    <div class="col-md-8">
      <div class="input-group mb-3">
        <form action="/">
          <input type="text" class="form-control" placeholder="Search by name" name="name"
                 v-model="name"/>
          <div class="input-group-append">
            <button class="btn btn-outline-secondary"
                    @click="searchTitle"
            >
              Search
            </button>
          </div>
        </form>
      </div>
    </div>
    <div class="col-md-12">
      <h4>Products List</h4>
      <div>
        <router-link to="/orders" class="nav-link">List orders</router-link>
      </div>
      <div>
        <table class="table table-striped table-bordered no-wrap">
          <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Thumbnail</th>
            <th>Price</th>
            <th>Status</th>
            <th>Actions</th>
          </tr>
          </thead>
          <tbody>
          <tr :class="{ active: index === currentIndex }" v-for="(product, index) in products"  v-bind:key="index">
            <td v-text="product.id"></td>
            <td v-text="product.name"></td>
            <td class="product-image"><img :src="product.thumbnails" alt="" width="50"></td>
            <td v-text="product.price"></td>
            <td v-text="product.status"></td>
            <td>Actions </td>
          </tr>
          </tbody>
        </table>
      </div>
      <div class="overflow-auto">
        <a-pagination
            :current="page"
            :page-size="limit"
            :total="totalItems"
            @change="handelClick"
            size="sm"
        ></a-pagination>
      </div>
  </div>
  </div>
</template>

<script>
import ProductDataService from "../service/ProductDataService";

export default {
  name: "list-products",
  data() {
    return {
      page: 1,
      limit: 5,
      totalItems: 0,
      currentPage: 1,
      products: [],
      currentIndex: -1,
      name: "",
      detail: ""
    };
  },
  methods: {
    retrieveTutorials() {
      let urlParams = new URLSearchParams(window.location.search);
      if (urlParams.has("name")) {
        // if (this.keyword === ""){
        this.name = urlParams.get("name");
        ProductDataService.getAllByName(this.page, this.limit, this.name)
            .then(response => {
              this.products = response.data.content;
              this.page = response.data.currentPage;
              this.totalItems = response.data.totalItems;
              console.log(response.data);
            })
            .catch(e => {
              console.log(e);
            });
      } else {
        ProductDataService.getAllBy(this.page, this.limit)
            .then(response => {
              this.products = response.data.content;
              this.page = response.data.currentPage;
              this.totalItems = response.data.totalItems;
              console.log(response.data);

            })
            .catch(e => {
              console.log(e);
            });
      }
    },
    handelClick(val){
      this.page = val;
      this.retrieveTutorials();
    },
    searchTitle() {
      this.page = 1;
    },
  },
  created() {
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