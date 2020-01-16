SELECT name FROM 
	(SELECT DISTINCT event_id FROM 
		(SELECT event_id, COUNT(medal) medals, result, COUNT(result) result_count
		FROM Results
		WHERE medal = 'GOLD'
		GROUP BY event_id, result) as r
	WHERE medals>1 AND result_count>1) as es
	INNER JOIN
	Events
	ON es.event_id = Events.event_id
	WHERE is_team_event = 1
