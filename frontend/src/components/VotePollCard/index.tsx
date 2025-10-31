import {Button, Card, CardContent, RadioGroup, Typography} from "@mui/material";
import {useState} from "react";

/*
interface VotePollCardProps {
  polldto: PollDto
}
*/

export default function VotePollCard() {
  const [selected, setSelected] = useState("");

  return(
    <Card elevation={0} sx={{width: '40%', borderRadius: '12px', border: '1px solid', borderColor: 'primary.light'}}>
      <CardContent sx={{display: 'flex', flexDirection: 'column'}}>
        <Typography variant='h6' color='primary.main' gutterBottom={true}>
          poll question
        </Typography>
        {/*inside radiooGroup, for every voteOption in poll, add formcontrolLabel to label radio buttons*/}
        <RadioGroup value={selected} onChange={(e) => setSelected(e.target.value)}>
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