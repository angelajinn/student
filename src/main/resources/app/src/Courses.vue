
<template>
  <div>
    <h2 style="text-align: center; font-family: 'Raleway', sans-serif; font-size: 45px; line-height: 0.3px; ">Sample App</h2>
    <h3 style="text-align: center; font-family: 'Raleway', sans-serif; font-size: 20px;">Course Info</h3>
  </div>

  <div>
    <form @submit.prevent="createCourse">
      <p>
        Course name:
        <input type="text" required placeholder="course name..." v-model="newCourse.name">
      </p>
      <button type="submit">Add course</button>
    </form>
  </div>

  <div>
    <h2>All Courses</h2>
    <ul>
      <table>
        <thead>
        <tr>
          <th>Name</th>
          <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="course in allCourses" :key="course.id">
          <td>{{ course.name }}</td>

          <td>
            <button @click="editCourse(course)">Edit</button>
            <button @click="deleteCourse(course.id)">Delete</button>
          </td>
        </tr>
        </tbody>
      </table>
    </ul>
  </div>

</template>

<script>

export default {
  name: "App",
  data() {
    return {
      newCourse: {
        name: ''
      },
      allCourses:[],

    };
  },
  created() {
    this.fetchCourses();
  },
  methods: {
    createCourse() {
      fetch("api/course", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          name: this.newCourse.name
        }),
      })
          .then(response => response.json())
          .then(() => {
            this.fetchCourses();
            this.newCourse = {name: ''};
          });
    },

    fetchCourses() {
      fetch('/api/course/')
          .then(response => response.json())
          .then(data => {
            this.allCourses = data;
          });
    },

    deleteCourse(courseId) {
      fetch(`/api/course/${courseId}`, {
        method: "DELETE",
      })
          .then(() => {
            this.allCourses = this.allCourses.filter(
                (course) => course.id !== courseId
            );
          })
          .catch((error) => {
            console.error("Cannot delete course (error):", error);
          });
    },

  }
};
</script>


<style scoped>

</style>