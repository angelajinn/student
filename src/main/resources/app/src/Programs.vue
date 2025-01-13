
<template>
  <div class="page-container">
    <div>
      <h2 style="text-align: center; font-family: 'Raleway', sans-serif; font-size: 45px; line-height: 0.3px; ">Sample App</h2>
      <h3 style="text-align: center; font-family: 'Raleway', sans-serif; font-size: 20px;">Program Info</h3>
    </div>

    <div class="create-form">
      <form @submit.prevent="createProgram">
        <div class="form-group">
          <label for="program">Create a new program:</label>
          <input type="text" required placeholder="program name..." v-model="newProgram.name">
        </div>

        <button type="submit">Add program</button>
      </form>
    </div>


    <div v-if="editingProgram">
      <div class="create-form">

        <hr class="new1">

        <h2>Edit Program</h2>
        <form @submit.prevent="updateProgram">
          <div class="form-row">
            <div class="form-group">
              <label for="programName">Program name:</label>
              <input type="text" required placeholder="program name..." v-model="editingProgram.name">
            </div>
          </div>
          <div class="edit-form-button">
            <button type="submit">Update</button>
            <button type="button" @click="cancelEdit">Cancel</button>
          </div>
        </form>
      </div>
    </div>

    <div class="table-wrapper">
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
              <div class="table-buttons">
              <button @click="editProgram(program)">Edit</button>
              <button @click="deleteProgram(program.id)">Delete</button>
              </div>
            </td>
          </tr>
          </tbody>
        </table>
      </ul>
    </div>
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
      editingProgram: null,

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
            this.allPrograms = data.reverse();
          });
    },

    editProgram(program) {
      this.editingProgram = {...program};
    },

    cancelEdit() {
      this.editingProgram = null;
    },

    updateProgram() {
      fetch(`/api/program/${this.editingProgram.id}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(this.editingProgram),
      })
          .then(response => response.json()) // Handle response
          .then(() => {
            this.fetchPrograms();
            this.editingProgram = null;
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

.create-form {
  max-width: 1000px;
  margin: 20px auto;
  font-family: 'Raleway', sans-serif;
}

form {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.form-row {
  display: flex;
  gap: 20px;
}

.form-group {
  flex: 1;
  display: flex;
  flex-direction: column;
}

label {
  margin-bottom: 2px;
  font-weight: bold;
  font-size: 14px;
}

input, select {
  padding: 10px;
  font-size: 14px;
  border: 1px solid #ccc;
  border-radius: 5px;
  box-sizing: border-box;
  width: 296px;
}

.page-container {
  margin-left: 50px;
  margin-right: 50px;
  margin-bottom: 200px;
}

input[type=date],
select {
  width: 296px;
  height: 43px;
  font-size: 13px;
  padding: 12px 20px;
  margin: 8px 0;
  box-sizing: border-box;
  border-radius: 10px;
}

input[type=text],
input[type=number],
select {
  width: 296px;
  font-size: 14px;
  padding: 12px 20px;
  margin: 8px 0;
  box-sizing: border-box;
  border-radius: 10px;
}

button {
  padding: 10px 20px;
  font-size: 14px;
  background-color: #D2E7D8;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  width: 147px;
}

button:hover {
  background-color: #B0D1B8;
}


.table-wrapper {
  width: 100%; /* Fixed width */
  max-width: 1200px;
  max-height: 400px; /* Maximum height */
  overflow-y: auto; /* Enable scrolling for excess rows */
  margin: 20px auto;
  display: flex;
  font-family: 'Raleway', sans-serif;
}

.edit-form-button {
  display:flex;
  gap: 10px;
}

table {
  width: 100%; /* Ensures table takes up the full width of its container */
  table-layout: fixed; /* Prevents column resizing based on content */
  border-collapse: collapse;
}

th {
  position: sticky;
  top: 0;
  background-color: #B0D1B8;
  z-index: 1;
  height: 50px;
}

td, td {
  padding: 8px;
  text-align: center;
  border-bottom: 1px solid #ddd;
}

.table-buttons {
  display: flex;
  gap: 10px;
  justify-content: center;
}

</style>