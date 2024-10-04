package org.example.automaticed.mapper;

import org.example.automaticed.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrderDao extends JpaRepository<OrderEntity,Long> {

}
