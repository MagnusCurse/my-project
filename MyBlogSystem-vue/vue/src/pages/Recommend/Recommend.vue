<script>

export default {
  name: "Recommend"

}
</script>

<template>
  <div class="destination">
    <div class="destination-card">
      <div class="destination-profile">
        <img class="profile-img" src="https://images.unsplash.com/photo-1508214751196-bcfd4ca60f91?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1650&q=80" alt="" />
        <div class="destination-length">
          <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-map-pin">
            <path d="M21 10c0 7-9 13-9 13s-9-6-9-13a9 9 0 0118 0z" />
            <circle cx="12" cy="10" r="3" />
          </svg>
          45.4m
        </div>
      </div>
      <div class="destination-points">
        <div class="point">Traffic Point</div>
        <div class="sub-point">Brooklyn St, NY</div>
      </div>
    </div>
    <div class="destination-card">
      <div class="destination-profile">
        <img class="profile-img" src="https://images.unsplash.com/photo-1570295999919-56ceb5ecca61?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1400&q=80" alt="" />
        <div class="destination-length">
          <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-map-pin">
            <path d="M21 10c0 7-9 13-9 13s-9-6-9-13a9 9 0 0118 0z" />
            <circle cx="12" cy="10" r="3" />
          </svg>
          42.8m
        </div>
      </div>
      <div class="destination-points">
        <div class="point">Pickup Point</div>
        <div class="sub-point">Maryland 17, NY</div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.destination {
  display: flex;
  align-items: center;
  margin-top: auto;
}
.destination-card {
  background: #1a1d3a;
  background: linear-gradient(45deg, #1a1d3a 0%, #212752 100%);
  padding: 20px;
  width: 100%;
  border-radius: 6px;
}
.destination-card + .destination-card {
  margin-left: 20px;
  background: #1a1d3a;
  background: linear-gradient(325deg, #1a1d3a 0%, #212752 100%);
}
.destination-profile {
  display: flex;
  align-items: center;
  font-size: 14px;
}
.destination-profile svg {
  width: 18px;
  flex-shrink: 0;
  margin-right: 8px;
}
.destination-length {
  margin-left: auto;
  display: flex;
  align-items: center;
  font-size: 13px;
}
.destination-points {
  margin-top: 30px;
}

.profile-img {
  width: 46px;
  height: 46px;
  object-fit: cover;
  border-radius: 50%;
  padding: 2px;
  border: 1px solid #bebec4;
  flex-shrink: 0;
}

.point {
  font-size: 14px;
  color: #bebec4;
  font-weight: 500;
}

.sub-point {
  font-size: 13px;
  margin-top: 4px;
}
</style>