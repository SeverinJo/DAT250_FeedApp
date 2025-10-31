export interface Poll {
  id: string;
  question: string;
  options: string[];
}

export interface PollCardProps {
  poll?: Poll;
  readOnly?: boolean;
  onPollCreated?: () => void;
  onClose?: () => void;
}
