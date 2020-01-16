SELECT Countries.name, COUNT(medal)/CAST(MIN(population) as float) medals_to_population FROM
	((SELECT event_id FROM Events WHERE is_team_event = 1) e
	INNER JOIN Results ON e.event_id = Results.event_id
	INNER JOIN Players ON Players.player_id = Results.player_id
	INNER JOIN Countries ON Countries.country_id = Players.country_id)
GROUP BY Countries.name 
ORDER BY medals_to_population ASC
LIMIT 5