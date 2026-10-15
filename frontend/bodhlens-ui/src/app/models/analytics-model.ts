import { UploadTrend } from "./upload-trend";

export interface AnalyticsModel{
    totalDocuments: number;
    totalChatSessions: number;
    totalUsers: number;
    totalQuestionsAsked: number;
    totalChunks: number;
    averageResponseTime: number;
    averageChunksPerDocument: number;
    mostQueriedDocument: string;
    mostActiveUser: string;
    uploadTrends: UploadTrend[]
}