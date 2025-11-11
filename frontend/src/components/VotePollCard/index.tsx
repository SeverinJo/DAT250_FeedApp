import {Button, Card, CardContent, FormControlLabel, Radio, RadioGroup, Typography} from "@mui/material";
import {useState} from "react";
import type {PollResponse} from "../../api/generated";

interface VotePollCardProps {
  poll: PollResponse
}

export default function VotePollCard({poll}: VotePollCardProps) {
  const [selected, setSelected] = useState("");

  return(
    <Card elevation={0} sx={{width: '40%', borderRadius: '12px', border: '1px solid', borderColor: 'primary.light', backgroundColor: 'transparent'}}>
      <CardContent sx={{display: 'flex', flexDirection: 'column'}}>
        <Typography variant='h6' color='primary.main' gutterBottom={true}>
          {poll.question}
        </Typography>

        <RadioGroup
          value={selected}
          onChange={(e) => setSelected(e.target.value)}
        >
          {poll.voteOptions?.map((option) => (
              <FormControlLabel
                  key={option.id}
                  value={option.id}
                  control={<Radio />}
                  label={option.caption}
              />
          ))}
        </RadioGroup>



        <Button
          variant="contained"
          color="primary"
          sx={{ mt: 2, width: 'fit-content', alignSelf: 'flex-start' }}
          // onClick={handleVote}
        >
          Vote
        </Button>
      </CardContent>
    </Card>
  );

}