<template>
  <div>
    <div>Hello World</div>
    <button
        type="button"
        @click="createStudent"
    >Create New Student
    </button>
  </div>
</template>
<script>
export default {
  name: "App",
  methods: {
    createStudent() {
      let response = fetch("/api/student", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          // 'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: JSON.stringify({
          firstName: "Joyce",
          lastName: "Ye",
          dob: null
        }), // body data type must match "Content-Type" header
      });
      return response.json();
    }
  }
}
</script>