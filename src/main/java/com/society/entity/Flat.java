package com.society.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "flats")
public class Flat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String flatNumber;
    private String block;
    private Integer floor;
    @OneToOne
    @JoinColumn(name = "owner_id")
    @JsonIgnore
    private User owner;
    public Flat() {}

    public Flat(Long id, String flatNumber, String block, Integer floor, User owner) {
        this.id = id;
        this.flatNumber = flatNumber;
        this.block = block;
        this.floor = floor;
        this.owner = owner;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFlatNumber() { return flatNumber; }
    public void setFlatNumber(String flatNumber) { this.flatNumber = flatNumber; }
    public String getBlock() { return block; }
    public void setBlock(String block) { this.block = block; }
    public Integer getFloor() { return floor; }
    public void setFloor(Integer floor) { this.floor = floor; }
    public User getOwner() { return owner; }
    public void setOwner(User owner) { this.owner = owner; }
}