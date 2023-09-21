package com.alienworkspace.testing.springtesting.repository;

import com.alienworkspace.testing.springtesting.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmail(String email);

    // define custom query using JPQL index parameters
    @Query("select em from Employee em where em.firstName=?1 and em.lastName=?2")
    Employee findByCustomJPQL(String firstName, String lastName);

    // define custom query using JPQL with named params
    @Query("select em from Employee em where em.firstName=:firstName and em.lastName=:lastName")
    Employee findByCustomJPQLNamedParams(@Param("firstName") String firstName, @Param("lastName") String lastName);

    // define custom query using Native SQL with index params
    @Query(value = "select em.* from employees as em where em.first_name=?1 and em.last_name=?2", nativeQuery = true)
    Employee findByNativeQuery(String firstName, String lastName);


    // define custom query using Native SQL with index params
    @Query(value = "select em.* from employees as em where em.first_name=:firstName and em.last_name=:lastName", nativeQuery = true)
    Employee findByNativeQueryNamedParams(@Param("firstName") String firstName, @Param("lastName") String lastName);
}
