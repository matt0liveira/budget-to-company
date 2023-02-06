package com.budget.budgetapi.infrasctruture.service.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.budget.budgetapi.domain.filter.TotalTransactionsWithoutDateFilter;
import com.budget.budgetapi.domain.filter.TransactionFilter;
import com.budget.budgetapi.domain.model.Transaction;
import com.budget.budgetapi.domain.model.dto.TotalTransactions;
import com.budget.budgetapi.domain.model.dto.TotalTransactionsByCurdate;
import com.budget.budgetapi.domain.model.dto.TotalTransactionsByDate;
import com.budget.budgetapi.domain.model.dto.TotalTransactionsByMonth;
import com.budget.budgetapi.domain.model.dto.TotalTransactionsByWeekCurrent;
import com.budget.budgetapi.domain.model.dto.TotalTransactionsLastFourYears;
import com.budget.budgetapi.domain.service.TransactionsAnalyticsQueryService;

@Repository
public class TransactionsAnalyticsQueryServiceImpl implements TransactionsAnalyticsQueryService {

    @Autowired
    private EntityManager manager;

    @Override
    public List<TotalTransactionsByDate> queryTotalTransactionsByDate(TransactionFilter filter) {
        var builder = manager.getCriteriaBuilder();
        var query = builder.createQuery(TotalTransactionsByDate.class);
        var root = query.from(Transaction.class);
        var predicates = new ArrayList<Predicate>();

        var selection = builder.construct(TotalTransactionsByDate.class,
                root.get("date"),
                builder.count(root.get("id")),
                builder.sum(root.get("value")));

        addPredicatesWithDate(filter, builder, root, predicates);

        query.select(selection);
        query.where(predicates.toArray(new Predicate[0]));
        query.groupBy(root.get("date"));

        return manager.createQuery(query).getResultList();
    }

    @Override
    public List<TotalTransactions> queryTotalTransactions(TransactionFilter filter) {
        var builder = manager.getCriteriaBuilder();
        var query = builder.createQuery(TotalTransactions.class);
        var root = query.from(Transaction.class);
        var predicates = new ArrayList<Predicate>();

        var selection = builder.construct(TotalTransactions.class,
                builder.count(root.get("id")),
                builder.sum(root.get("value")));

        addPredicatesWithDate(filter, builder, root, predicates);

        query.select(selection);
        query.where(predicates.toArray(new Predicate[0]));

        return manager.createQuery(query).getResultList();
    }

    @Override
    public List<TotalTransactionsLastFourYears> queryTotalTransactionsLastFourYears(
            TotalTransactionsWithoutDateFilter filter) {
        var builder = manager.getCriteriaBuilder();
        var query = builder.createQuery(TotalTransactionsLastFourYears.class);
        var root = query.from(Transaction.class);
        var predicates = new ArrayList<Predicate>();

        var functionYear = builder.function("year", Integer.class, root.get("date"));

        var selection = builder.construct(TotalTransactionsLastFourYears.class,
                functionYear,
                builder.count(root.get("id")),
                builder.sum(root.get("value")));

        addPredicatesWithoutDate(filter, builder, root, predicates);

        var functionCurdate = builder.function("curdate", Date.class);
        var functionSubDate = builder.function("subdate", Date.class, functionCurdate, builder.literal(1460));
        // var functionDateSub = builder.function("date_sub", Date.class,
        // functionCurdate,
        // new LiteralExpression<>(null, null, null));

        predicates.add(builder.between(root.get("date"), functionSubDate,
                functionCurdate));

        query.select(selection);
        query.where(predicates.toArray(new Predicate[0]));
        query.groupBy(functionYear);

        return manager.createQuery(query).getResultList();
    }

    @Override
    public List<TotalTransactionsByCurdate> queryTotalTransactionsByCurdate(
            TotalTransactionsWithoutDateFilter filter) {
        var builder = manager.getCriteriaBuilder();
        var query = builder.createQuery(TotalTransactionsByCurdate.class);
        var root = query.from(Transaction.class);
        var predicates = new ArrayList<Predicate>();

        var functionCurdate = builder.function("curdate", Date.class);
        var selection = builder.construct(TotalTransactionsByCurdate.class,
                functionCurdate,
                builder.count(root.get("id")),
                builder.sum(root.get("value")));

        addPredicatesWithoutDate(filter, builder, root, predicates);

        predicates.add(builder.equal(root.get("date"), functionCurdate));

        query.select(selection);
        query.where(predicates.toArray(new Predicate[0]));

        return manager.createQuery(query).getResultList();
    }

    @Override
    public List<TotalTransactionsByMonth> queryTotalTransactionsByMonths(
            TotalTransactionsWithoutDateFilter filter) {
        var builder = manager.getCriteriaBuilder();
        var query = builder.createQuery(TotalTransactionsByMonth.class);
        var root = query.from(Transaction.class);
        var predicates = new ArrayList<Predicate>();

        var functionMonthName = builder.function("monthname", String.class, root.get("date"));
        var functionMonth = builder.function("month", Integer.class, root.get("date"));

        var selection = builder.construct(TotalTransactionsByMonth.class,
                functionMonthName,
                functionMonth,
                builder.count(root.get("id")),
                builder.sum(root.get("value")));

        addPredicatesWithoutDate(filter, builder, root, predicates);

        List<Integer> months = Arrays.asList(new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 });
        predicates.add(functionMonth.in(months));

        query.select(selection);
        query.where(predicates.toArray(new Predicate[0]));
        query.groupBy(functionMonth);

        return manager.createQuery(query).getResultList();
    }

    @Override
    public List<TotalTransactionsByWeekCurrent> queryTotalTransactionsByWeekCurrent(
            TotalTransactionsWithoutDateFilter filter) {
        var builder = manager.getCriteriaBuilder();
        var query = builder.createQuery(TotalTransactionsByWeekCurrent.class);
        var root = query.from(Transaction.class);
        var predicates = new ArrayList<Predicate>();

        var functionDayName = builder.function("dayname", String.class, root.get("date"));
        var functionDayOfWeek = builder.function("dayofweek", Integer.class, root.get("date"));
        var functionYearWeekOfFieldDate = builder.function("yearweek", Long.class,
                root.get("date"),
                builder.literal(0));
        var functionCurdate = builder.function("curdate", Date.class);
        var functionYearWeekOfCurdate = builder.function("yearweek", Long.class,
                functionCurdate,
                builder.literal(0));

        var selection = builder.construct(TotalTransactionsByWeekCurrent.class,
                functionDayName,
                functionDayOfWeek,
                builder.count(root.get("id")),
                builder.sum(root.get("value")));

        addPredicatesWithoutDate(filter, builder, root, predicates);

        predicates.add(builder.equal(functionYearWeekOfFieldDate, functionYearWeekOfCurdate));

        query.select(selection);
        query.where(predicates.toArray(new Predicate[0]));
        query.groupBy(functionDayOfWeek);

        return manager.createQuery(query).getResultList();
    }

    private void addPredicatesWithDate(TransactionFilter filter, CriteriaBuilder builder,
            Root<Transaction> root,
            ArrayList<Predicate> predicates) {
        if (filter.getUserId() != null) {
            predicates.add(builder.equal(root.get("user"), filter.getUserId()));
        }

        if (filter.getType() != null) {
            predicates.add(builder.equal(root.get("type"), filter.getType()));
        }

        if (filter.getDateTransactionInitial() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("date"), filter.getDateTransactionInitial()));
        }

        if (filter.getDateTransactionFinal() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("date"), filter.getDateTransactionFinal()));
        }
    }

    private void addPredicatesWithoutDate(TotalTransactionsWithoutDateFilter filter, CriteriaBuilder builder,
            Root<Transaction> root,
            ArrayList<Predicate> predicates) {
        if (filter.getType() != null) {
            predicates.add(builder.equal(root.get("type"), filter.getType()));
        }

        if (filter.getUserId() != null) {
            predicates.add(builder.equal(root.get("user"), filter.getUserId()));
        }

        if (filter.getCategoryId() != null) {
            predicates.add(builder.equal(root.get("category"), filter.getCategoryId()));
        }
    }
}