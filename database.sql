USE currencydb;


SELECT * FROM Currency;


SELECT *
FROM Currency
WHERE abbreviation = 'EUR';

-- 3. Count total currencies
SELECT COUNT(*) AS total_currencies
FROM Currency;


SELECT *
FROM Currency
ORDER BY rate_to_usd DESC
    LIMIT 1;