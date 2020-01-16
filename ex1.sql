SELECT birthyear, COUNT(p.player_id) "num-players", SUM(r.medals) "num-gold-medals" FROM
	(SELECT EXTRACT(YEAR FROM birthdate) birthyear, player_id
	FROM Players) p
	INNER JOIN
	(SELECT player_id, COUNT(medal) medals
	FROM Results 
	WHERE medal = 'GOLD'
	GROUP BY player_id) r
	ON p.player_id = r.player_id
GROUP BY birthyear