package com.hospitalmanagementsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
    private Connection connection;
    private Scanner scanner;

    public Patient(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    public void addPatients() {
        System.out.println("Enter Patient name: ");
        String name = scanner.nextLine();
        System.out.println("Enter Patient age: ");
        int age = scanner.nextInt();
        System.out.println("Enter Patient gender: ");
        String gender = scanner.next();

        try {
            String query = "INSERT INTO Patients (name, age, gender) VALUES(?,?,?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            statement.setInt(2, age);
            statement.setString(3, gender);
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Patient Added Successfully!!");
            } else {
                System.out.println("Failed to Add Patient!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void viewPatients() {
        String query = "select * from Patients";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Patients: ");
            System.out.println("+------------+-------------------+----------+---------------+");
            System.out.println("| Patient Id | Patient Name      | Age      | Gender        ");
            System.out.println("+------------+-------------------+----------+---------------+");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");
                System.out.printf("|%-13s|%-20s|%-11s|%-16s|\n ", id, name, age, gender);
                System.out.println("+------------+-------------------+----------+---------------+");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean getPatientById(int id) {
        String query = "SELECT * FROM Patients WHERE id=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
