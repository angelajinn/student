
<template>
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
      <button type="submit">Add student</button>
    </form>
  </div>

  <div>
  <h2>All Students</h2>
  <ul>
    <li v-for="student in allStudents" :key="student.id">
      {{ student.firstName }} {{ student.lastName }}
      <button @click="editStudent(student)">Edit</button>
      <button @click="deleteStudent(student.id)">Delete</button>
    </li>
  </ul>
  </div>

  <div v-if="deletingStudent">
    <h2>Delete Student</h2>
    <p>Are you sure you want to delete this student?</p>
    <button @click="confirm">Yes</button>
    <button @click="cancel">No</button>
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
      <button type="submit">Update student</button>
    </form>
  </div>

</template>

<script>

import students from "./store/modules/entities/students";

export default {
  name: "App",
  data() {
    return {
      newStudent: {
        firstName: '',
        lastName: '',
        dateOfBirth: '',
        gpa: ''
      },
      allStudents: [], // array that stores the list of all students
      editingStudent: null,
      deletingStudent: null
    };
  },
  created() {
    this.fetchStudents();
  },
  methods: {
    editStudent(student) { // set the student to be edited
      this.editingStudent = { ...student };
    },

    updateStudent() {
      fetch(`/api/student/${this.editingStudent.id}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          firstName: this.editingStudent.firstName,
          lastName: this.editingStudent.lastName,
          dateOfBirth: this.editingStudent.dateOfBirth,
          gpa: this.editingStudent.gpa
        }),
      })
          .then(response => response.json()) // Handle response
          .then(() => {
            this.fetchStudents();
            this.editingStudent = null;
          });
    },

    fetchStudents() {
      fetch('/api/student/')
          .then(response => response.json()) //  parses JSON
          .then(data => {
            this.allStudents = data; // Populate allStudents array
          });
    },

    createStudent() {
      let response = fetch("/api/student", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          // 'Content-Type': 'application/x-www-form-urlencoded',v
        },
        body: JSON.stringify({
          firstName: this.newStudent.firstName,
          lastName: this.newStudent.lastName,
          dateOfBirth: this.newStudent.dateOfBirth,
          gpa: this.newStudent.gpa
        }), // body data type must match "Content-Type" header
      })
          // when sending a student to the API, the backend responds in JSON format. "response.json() makes sure that
          // the body of the response is parsed into a JS object
          .then(response => response.json()) // Handle response
          .then(() => {
            this.fetchStudents(); // Refresh the student list after adding a new student (fetch the latest list)
            // Clear the input fields after successful submission
            this.newStudent = {firstName: '', lastName: '', dateOfBirth: '', gpa: ''};
          });
    },
  }
};
</script>