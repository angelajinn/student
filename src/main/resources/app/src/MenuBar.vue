<template>
  <div>
    <nav class ="menu-bar">
      <button @click="handleClick('Students')">Students</button>
      <button @click="handleClick('Programs')">Programs</button>
      <button @click="handleClick('Courses')">Courses</button>
    </nav>
  </div>
</template>

<script>
export default {
  name: "MenuBar",
  methods: {
    handleClick(buttonName) {
      console.log(`${buttonName} button clicked`);
      // You can add navigation logic here, e.g., using Vue Router
    }
  }
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Raleway:wght@400;700&display=swap');


.menu-bar {
  display: flex;
  justify-content: center;
  gap: 110px;
  padding-top: 20px;
  /* background-color: #333;
  // padding: 10px; */
}

.menu-bar button {
  background-color: #D2E7D8;
  border: none;
  color: black;
  padding: 14px 20px;
  text-align: center;
  font-size: 16px;
  margin: 4px 2px;
  cursor: pointer;
  -webkit-transition-duration: 0.4s; /* Safari */
  transition-duration: 0.4s;
  border-radius: 40px;
  width: 170px;
  font-family: 'Raleway', sans-serif;
  font-style: normal;
}

.menu-bar button:hover {
  box-shadow: 0 12px 16px 0 rgba(0,0,0,0.24), 0 17px 50px 0 rgba(0,0,0,0.19);
}

</style>