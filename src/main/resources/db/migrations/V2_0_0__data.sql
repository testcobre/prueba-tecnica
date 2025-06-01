
INSERT INTO notification_event(event_id, event_type, content, delivery_date, delivery_status) VALUES
('EVT001', 'credit_card_payment', 'Credit card payment received for $150.00', '2024-03-15T09:30:22Z', 'completed'),
('EVT002', 'debit_card_withdrawal', 'ATM withdrawal of $200.00', '2024-03-15T10:15:45Z', 'completed'),
('EVT003', 'credit_transfer', 'Bank transfer received from Account #4567 for $1,500.00', '2024-03-15T11:20:18Z', 'failed'),
('EVT004', 'debit_automatic_payment', 'Monthly utility bill payment of $85.50', '2024-03-15T12:05:33Z', 'completed'),
('EVT005', 'credit_refund', 'Refund processed for order #789 for $45.99', '2024-03-15T13:45:10Z', 'failed'),
('EVT006', 'debit_transfer', 'Money transfer sent to Account #8901 for $500.00', '2024-03-15T14:30:55Z', 'completed'),
('EVT007', 'credit_deposit', 'Direct deposit received from Employer XYZ for $2,500.00', '2024-03-15T15:20:40Z', 'completed'),
('EVT008', 'debit_purchase', 'Point of sale purchase at Store ABC for $75.25', '2024-03-15T16:10:15Z', 'completed'),
('EVT009', 'credit_cashback', 'Cashback reward credited for $25.00', '2024-03-15T17:25:30Z', 'failed'),
('EVT010', 'debit_subscription', 'Monthly streaming service payment of $14.99', '2024-03-15T18:05:12Z', 'completed');