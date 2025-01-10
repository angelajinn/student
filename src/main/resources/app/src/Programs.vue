
<template>
  <div>
    <h2 style="text-align: center; font-family: 'Raleway', sans-serif; font-size: 45px; line-height: 0.3px; ">Sample App</h2>
    <h3 style="text-align: center; font-family: 'Raleway', sans-serif; font-size: 20px;">Programs</h3>
  </div>

  <div>
    <h2>Create a new program</h2>
    <form @submit.prevent="createProgram">
      <p>
        Program name:
        <input type="text" required placeholder="program name..." v-model="newProgram.name">
      </p>
      <button type="submit">Add program</button>
    </form>
  </div>

  <div>
    <h2>All Programs</h2>
    <ul>
      <table>
        <thead>
        <tr>
          <th>Name</th>
          <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="program in allPrograms" :key="program.id">
          <td>{{ program.name }}</td>

          <td>
            <button @click="editProgram(program)">Edit</button>
            <button @click="deleteProgram(program.id)">Delete</button>
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
      newProgram: {
        name: ''
      },
      allPrograms:[],

    };
  },
  created() {
    this.fetchPrograms();
  },
  methods: {
    createProgram() {
      fetch("api/program", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          name: this.newProgram.name
        }),
      })
          .then(response => response.json())
          .then(() => {
            this.fetchPrograms();
            this.newProgram = {name: ''};
          });
    },

    fetchPrograms() {
      fetch('/api/program/')
          .then(response => response.json())
          .then(data => {
            this.allPrograms = data;
          });
    },

    deleteProgram(programId) {
      fetch(`/api/program/${programId}`, {
        method: "DELETE",
      })
          .then(() => {
            this.allPrograms = this.allPrograms.filter(
                (program) => program.id !== programId
            );
          })
          .catch((error) => {
            console.error("Cannot delete program (error):", error);
          });
    },

  }
};
</script>


<style scoped>

</style>