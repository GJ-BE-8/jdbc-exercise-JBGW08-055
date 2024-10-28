package com.nhnacademy.jdbc.student.repository.impl;

import com.nhnacademy.jdbc.student.domain.Student;
import com.nhnacademy.jdbc.student.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import java.sql.*;
import java.util.Optional;

@Slf4j
public class StudentRepositoryImpl implements StudentRepository {

    @Override
    public int save(Connection connection, Student student){
        //todo#2 학생등록
        String sql = "insert into jdbc_students(id,name,gender,age) values(?,?,?,?)";

        try( PreparedStatement preparedStatement = connection.prepareStatement(sql); )
        {
            preparedStatement.setString(1,student.getId());
            preparedStatement.setString(2,student.getName());
            preparedStatement.setString(3,student.getGender().toString());
            preparedStatement.setInt(4,student.getAge());

            int result = preparedStatement.executeUpdate();
            log.debug("save : {}",result);
            return result;

        }
        catch (SQLException e)
        {

            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Student> findById(Connection connection,String id){
        //todo#3 학생조회

        ResultSet rs = null;
        String sql = "select * from jdbc_students where id=?";
        log.debug("sql : {}",sql);

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {
            preparedStatement.setString(1,id);

            rs = preparedStatement.executeQuery();

            if(rs.next())
            {
                Student student = new Student(
                                                rs.getString("id"),
                                                rs.getString("name"),
                                                Student.GENDER.valueOf(rs.getString("gender")),
                                                rs.getInt("age"),
                                                rs.getTimestamp("created_at").toLocalDateTime()
                                                );

                return Optional.of(student);
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public int update(Connection connection,Student student){
        //todo#4 학생수정

        String sql = "update jdbc_students set name=?, gender=?, age=? where id=?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {
            preparedStatement.setString(1,student.getName());
            preparedStatement.setString(2,student.getGender().toString());
            preparedStatement.setInt(3,student.getAge());
            preparedStatement.setString(4,student.getId());

            int result = preparedStatement.executeUpdate();
            log.debug("update : {}",result);
            return result;
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteById(Connection connection,String id){
        //todo#5 학생삭제
        String sql = "delete from jdbc_students where id=?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {
            preparedStatement.setString(1,id);

            int result = preparedStatement.executeUpdate();
            log.debug("delete : {}",result);
            return result;
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

}