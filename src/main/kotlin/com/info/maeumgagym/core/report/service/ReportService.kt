package com.info.maeumgagym.core.report.service

import com.info.common.responsibility.UseCase
import com.info.maeumgagym.core.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.core.common.exception.BusinessLogicException
import com.info.maeumgagym.pickle.port.out.ReadPickleCommentPort
import com.info.maeumgagym.pickle.port.out.ReadPicklePort
import com.info.maeumgagym.pickle.port.out.ReadPickleReplyPort
import com.info.maeumgagym.report.dto.request.ReportRequest
import com.info.maeumgagym.report.model.Report
import com.info.maeumgagym.report.model.ReportType
import com.info.maeumgagym.report.port.`in`.ReportPickleCommentUseCase
import com.info.maeumgagym.report.port.`in`.ReportPickleReplyUseCase
import com.info.maeumgagym.report.port.`in`.ReportPickleUseCase
import com.info.maeumgagym.report.port.`in`.ReportUserUseCase
import com.info.maeumgagym.report.port.out.SaveReportPort

@UseCase
internal class ReportService(
    private val readCurrentUserPort: ReadCurrentUserPort,
    private val saveReportPort: SaveReportPort,
    private val readUserPort: com.info.maeumgagym.core.user.port.out.ReadUserPort,
    private val readPicklePort: ReadPicklePort,
    private val readPickleCommentPort: ReadPickleCommentPort,
    private val readPickleReplyPort: ReadPickleReplyPort
) : ReportUserUseCase, ReportPickleUseCase, ReportPickleCommentUseCase, ReportPickleReplyUseCase {

    override fun reportUser(request: ReportRequest, nickname: String) {
        val user = readCurrentUserPort.readCurrentUser()

        if (user.nickname == nickname) {
            throw BusinessLogicException.CANNNOT_REPORT_ONESELF
        }

        val reportedUser = readUserPort.readByNickname(nickname) ?: throw BusinessLogicException.USER_NOT_FOUND

        saveReportPort.save(
            Report(
                type = ReportType.USER,
                reason = request.reason,
                reporterId = user.id!!,
                targetId = reportedUser.id!!
            )
        )
    }

    override fun reportPickle(request: ReportRequest, videoId: String) {
        val user = readCurrentUserPort.readCurrentUser()

        val pickle = readPicklePort.readById(videoId) ?: throw BusinessLogicException.PICKLE_NOT_FOUND

        if (user == pickle.uploader) {
            throw BusinessLogicException.CANNNOT_REPORT_ONESELF
        }

        saveReportPort.save(
            Report(
                type = ReportType.PICKLE,
                reason = request.reason,
                reporterId = user.id!!,
                targetId = videoId
            )
        )
    }

    override fun reportPickleComment(request: ReportRequest, id: Long) {
        val user = readCurrentUserPort.readCurrentUser()

        val pickleComment = readPickleCommentPort.readById(id)
            ?: throw BusinessLogicException.PICKLE_COMMENT_NOT_FOUND

        if (user == pickleComment.writer) {
            throw BusinessLogicException.CANNNOT_REPORT_ONESELF
        }

        saveReportPort.save(
            Report(
                type = ReportType.PICKLE_COMMENT,
                reason = request.reason,
                reporterId = user.id!!,
                targetId = id
            )
        )
    }

    override fun reportPickleReply(request: ReportRequest, id: Long) {
        val user = readCurrentUserPort.readCurrentUser()

        val pickleReply = readPickleReplyPort.readById(id) ?: throw BusinessLogicException.PICKLE_REPLY_NOT_FOUND

        if (user == pickleReply.writer) {
            throw BusinessLogicException.CANNNOT_REPORT_ONESELF
        }

        saveReportPort.save(
            Report(
                type = ReportType.PICKLE_REPLY,
                reason = request.reason,
                reporterId = user.id!!,
                targetId = id
            )
        )
    }
}
