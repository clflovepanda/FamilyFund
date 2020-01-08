package com.panda.family.dao;

import com.panda.family.domain.Deduction;
import com.panda.family.domain.Family;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DeductionDao {

    long insertDeduction(Deduction deduction);

    void removeDeduction(@Param("id") long id);

    List<Deduction> queryDeductionByUserId(@Param("userId") long userId);

    Deduction queryDeductionByUserIdAndItemName(@Param("userId") long userId, @Param("itemName") String itemName);

    Deduction queryDeductionById(@Param("id") long id);

}
