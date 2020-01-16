SELECT name FROM
	((SELECT country_id, SUM(CASE WHEN lower(substring(name from 1 for 1)) IN ('a', 'i', 'y','o','e','u') THEN 1 ELSE 0 END)/CAST(COUNT(name) as float) name_ratio 
	FROM Players
	GROUP BY country_id
	ORDER BY name_ratio DESC 
	LIMIT 1) as cp
	INNER JOIN Countries On Countries.country_id = cp.country_id) as cs
