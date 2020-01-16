SELECT Players.name FROM
	(SELECT player_id, olympic_id, COUNT(CASE WHEN medal = 'GOLD' THEN 1 END) as gold_medals, COUNT(CASE WHEN medal = 'SILVER' THEN 1 END) as silver_medals, COUNT(CASE WHEN medal = 'BRONZE' THEN 1 END) as bronze_medals
		FROM (SELECT player_id, medal, olympic_id
		FROM Results
		INNER JOIN Events ON Results.event_id = Events.event_id) as r
	GROUP BY player_id, olympic_id) as po
	INNER JOIN Players ON Players.player_id = po.player_id
WHERE gold_medals>0 AND silver_medals>0 AND bronze_medals > 0
	

