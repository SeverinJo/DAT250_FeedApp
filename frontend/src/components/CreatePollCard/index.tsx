import {Box, Button, Paper, TextField, Typography} from "@mui/material";
import {useState} from "react";


export default function CreatePollCard() {
  const [question, setQuestion] = useState("")
  const [options, setOptions] = useState(["", ""])
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState<string | null>(null)

  const handleOptionChange = (index: number, value: string) => {
    const newOptions = [...options]
    newOptions[index] = value
    setOptions(newOptions)
  }

  const addOption = () => setOptions([...options, ""])

  const handleSubmit = () => {
    setLoading(true);
    setError(null);

    // TODO: create poll dto, create some poll creation api method
  }

  return(
    <Paper
      elevation={0}
      sx={{
        display: 'flex',
        flexDirection: 'column',
        borderRadius: '12px',
        border: '1px solid',
        borderColor: 'primary.main',
        p: 3,
        backgroundColor: 'transparent'
    }}>
      <Typography variant="h5" color='primary.main' gutterBottom={true}>
        Create a Poll
      </Typography>
      <TextField
        label="Question"
        fullWidth
        margin="normal"
        value={question}
        onChange={(e) => setQuestion(e.target.value)}
      />
      {options.map((opt, i) => (
        <TextField
          key={i}
          label={`Option ${i + 1}`}
          fullWidth
          margin="normal"
          value={opt}
          onChange={(e) => handleOptionChange(i, e.target.value)}
        />
      ))}
      {error && <Typography color={"error"}>{error}</Typography>}
      <Box sx={{ mt: 2 }}>
        <Button onClick={addOption} variant={'outlined'} sx={{ mr: 1 }}>
          Add option
        </Button>
        <Button onClick={handleSubmit} variant={'contained'} disabled={loading}>
          {loading ? "Creating..." : "Create Poll"}
        </Button>
      </Box>
    </Paper>
  );

}