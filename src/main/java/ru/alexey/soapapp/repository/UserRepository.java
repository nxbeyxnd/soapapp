package ru.alexey.soapapp.repository;
/* 
18.06.2022: Alexey created this file inside the package: ru.alexey.soapapp.repository 
*/

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alexey.soapapp.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
