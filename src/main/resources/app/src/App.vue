
<template>
<div id ="app">
  <MenuBar />

  <h2 style="text-align: center; font-family: 'Raleway', sans-serif; font-size: 45px;">Sample App</h2>

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

  <div>
    <h2>Create a new student</h2>
    <form @submit.prevent="createStudent">
      <p>
        First name:
        <input type="text" required placeholder="first name..." v-model="newStudent.firstName">
      </p>
      <p>
        Last name:
        <input type="text" placeholder="last name..." v-model="newStudent.lastName">
      </p>
      <p>
        Date of birth:
        <input type="date" placeholder="dob..." v-model="newStudent.dateOfBirth">
      </p>
      <p>
        GPA:
        <input type="number" placeholder="gpa..." v-model="newStudent.gpa" step="0.01">
      </p>
      <p>
        Program:
        <select v-model="newStudent.program">
          <option disabled value="">Please select one</option>
          <option v-for="program in allPrograms" :key="program.id" :value="program.id">
            {{ program.name }}
          </option>
        </select>
      </p>
      <button type="submit">Add student</button>
    </form>
  </div>

  <div>
  <h2>All Students</h2>
  <ul>
    <table>
      <thead>
      <tr>
        <th>First Name</th>
        <th>Last Name</th>
        <th>Date of Birth</th>
        <th>GPA</th>
        <th>Program</th>
        <th>Actions</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="student in allStudents" :key="student.id">
        <td>{{ student.firstName }}</td>
        <td>{{ student.lastName }}</td>
        <td>{{ student.dateOfBirth }}</td>
        <td>{{ student.gpa }}</td>
        <td>{{ student.program ? student.program.name : 'N/A' }}</td> <!-- Handle Program association -->
        <td>
          <button @click="editStudent(student)">Edit</button>
          <button @click="deleteStudent(student.id)">Delete</button>
        </td>
      </tr>
      </tbody>
    </table>

  </ul>
  </div>

  <div v-if="editingStudent">
    <h2>Edit Student</h2>
    <form @submit.prevent="updateStudent">
      <p>
        First name:
        <input type="text" required placeholder="first name..." v-model="editingStudent.firstName">
      </p>
      <p>
        Last name:
        <input type="text" placeholder="last name..." v-model="editingStudent.lastName">
      </p>
      <p>
        Date of birth:
        <input type="date" placeholder="dob..." v-model="editingStudent.dateOfBirth">
      </p>
      <p>
        GPA:
        <input type="number" placeholder="gpa..." v-model="editingStudent.gpa" step="0.01">
      </p>
      <p>
        Program:
        <select v-model="editingStudent.program">
          <option disabled value="">Please select one</option>
          <option v-for="program in allPrograms" :key="program.id" :value="program.id">
            {{ program.name }}
          </option>
        </select>
      </p>
      <button type="submit">Update student</button>
    </form>
  </div>
  </div>

</template>

<script>

import MenuBar from "./MenuBar.vue"
import students from "./store/modules/entities/students";

export default {
  name: "App",
  components: {
    MenuBar
  },
  data() {
    return {
      newStudent: {
        firstName: '',
        lastName: '',
        dateOfBirth: '',
        gpa: '',
        program: ''
      },
      allStudents: [], // array that stores the list of all students
      editingStudent: null,

      newProgram: {
        name: ''
      },
      allPrograms:[],

    };
  },
  created() {
    this.fetchStudents();
    this.fetchPrograms();
  },
  methods: {
    editStudent(student) { // set the student to be edited
      this.editingStudent = { ...student };
      this.editingStudent.program = student.program ? student.program.id : null;
    },

    fetchStudents() {
      fetch('/api/student/')
          .then(response => response.json()) //  parses JSON
          .then(data => {
            this.allStudents = data; // Populate allStudents array
          });
    },

    updateStudent() {
      this.editingStudent.program = this.editingStudent.program ? {id: this.editingStudent.program} : null;
      fetch(`/api/student/${this.editingStudent.id}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify( this.editingStudent ),
      })
          .then(response => response.json()) // Handle response
          .then(() => {
            this.fetchStudents();
            this.editingStudent = null;
          });
    },

    deleteStudent(studentId) {
      fetch(`/api/student/${studentId}`, {
        method: "DELETE",
      })
          .then(() => {
            this.allStudents = this.allStudents.filter(
                (student) => student.id !== studentId
            );
          })
          .catch((error) => {
            console.error("Cannot delete student (error):", error);
          });
    },

    createStudent() {
      fetch("/api/student", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          // 'Content-Type': 'application/x-www-form-urlencoded',v
        },
        body: JSON.stringify({
          firstName: this.newStudent.firstName,
          lastName: this.newStudent.lastName,
          dateOfBirth: this.newStudent.dateOfBirth,
          gpa: this.newStudent.gpa,
          program: {
            id: this.newStudent.program // send program ID
          }
        }), // body data type must match "Content-Type" header
      })
          // when sending a student to the API, the backend responds in JSON format. "response.json() makes sure that
          // the body of the response is parsed into a JS object
          .then(response => response.json()) // Handle response
          .then(() => {
            this.fetchStudents(); // Refresh the student list after adding a new student (fetch the latest list)
            // Clear the input fields after successful submission
            this.newStudent = {firstName: '', lastName: '', dateOfBirth: '', gpa: '', program: ''};
          });
    },

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

<!--<style scoped>-->
<!--#app {-->
<!--  background-color: #ffffff;-->
<!--  min-height: 100vh; /* Ensure it covers the full screen */-->
<!--}-->
<!--</style>-->